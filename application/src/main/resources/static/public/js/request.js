var bdRequest = (function() {

    var InventoryItem = Backbone.Model.extend({
        defaults: {
            ref1: '',
            ref2: '',
            ref3: '',
            ref4: '',
            ref5: '',
            status: null,
            type: null
        }
    });

    var InventoryItems = Backbone.Collection.extend({
        model: InventoryItem
    });

    var inventoryItems = new InventoryItems();
    var selectedInventoryItems = [];
    var requestType = '';

    var RecordRowView = Marionette.View.extend({
        tagName: 'tr',
        template: '#record-row-template',
    });

    var EmptyRecordRowView = Marionette.View.extend({
        template: _.template('No Records. Start adding records.')
    });

    var RecordsTableView = Marionette.CollectionView.extend({
        tagName: 'tbody',
        childView: RecordRowView,
        emptyView: EmptyRecordRowView
    });

    var RecordsView = Marionette.View.extend({
        template: '#records-template',

        regions: {
            body: {
                el: 'tbody',
                replaceElement: true
            }
        },

        initialize: function() {

        },

        events: {
            'click #addRecordsButton': 'showRecordsModal',
            'click #okModalButton': 'addRecords'
        },

        addRecords: function(e) {
            var dataTable = $('#recordsDataTable').DataTable();
            var rowsSelected = dataTable.rows(['.selected']).data().toArray();

            var inventoryItemsIds = [];

            inventoryItems.reset();
            _.each(rowsSelected, function(row, i){
                var inventoryItem = new InventoryItem(row);
                inventoryItems.add(inventoryItem);
                inventoryItemsIds.push(row.id);
            }, this);

            $('#inventoryItemsField').val(inventoryItemsIds.toString());

            $('#recordsModal').modal('hide');

        },

        showRecordsModal: function(e){
            e.preventDefault();

            var storageTypeSelection = $('#storagetypesselect2').select2('data')[0];
            if(storageTypeSelection){
                var storageType = storageTypeSelection.text;

                $('#recordsModal').modal({
                    show: true,
                    backdrop: 'static',
                    keyboard: false
                });

                var datatable = $('#recordsDataTable').DataTable( {
                    retrieve: true,
                    searching: true,
                    ordering: false,
                    rowId: 'id',
                    ajax : {
                        url : "/api/inventoryitems?storageType=" + storageType + "&requestType=" + bdRequest.requestType,
                        dataSrc : ''
                    },
                    columns: [
                        { data: "id" },
                        { data: "id" },
                        { data: storageType.toLowerCase() +"Barcode" },
                        { data: "ref1" },
                        { data: "ref2" },
                        { data: "ref3" },
                        { data: "ref4" },
                        { data: "ref5" }
                    ],
                    columnDefs: [
                        {
                            targets: 0,
                            checkboxes: {
                                'selectRow': true
                            }
                        }
                    ],
                    select: {
                        style: 'multi'
                    },
                    createdRow: function( row, data, dataIndex ) {
                        var barcode = '';
                        if(data.type === 'DOCUMENT'){
                            barcode = data.documentBarcode;
                        }
                        else if(data.type === 'FILE'){
                            barcode = data.fileBarcode;
                        }
                        else {
                            barcode = data.boxBarcode;
                        }

                        $(row).attr('data-barcode', barcode);

                    },
                } );

                $('#recordsModal').on('shown.bs.modal', function() {
                    var dataTable = $('#recordsDataTable').DataTable();
                    dataTable.columns.adjust();

                    if(selectedInventoryItems){
                        _.each(selectedInventoryItems, function (i) {
                            dataTable.row('#'+i).select();
                        });
                    }
                });
            }
            else{
                swal("Error!", "Please select a Storage Type first!", "error");
            }

        },

        onRender: function() {
            this.showChildView('body', new RecordsTableView({collection: inventoryItems}));
        },

    });


    var App = Marionette.Application.extend({
        region: '#app',

        onStart: function() {
            var recordsView = new RecordsView();
            this.showView(recordsView);
        }
    });

    var app = new App();
    app.start();

    return {
        InventoryItem: InventoryItem,
        inventoryItems: inventoryItems,
        selectedInventoryItems: selectedInventoryItems,
        requestType: requestType

    };




})();

$(function() {
    $('#storagetypesselect2').on('select2:select', function (e) {
        if ( $.fn.DataTable.isDataTable( '#recordsDataTable' ) ) {
            var dataTable = $('#recordsDataTable').DataTable();
            dataTable.destroy();
            bdRequest.inventoryItems.reset();
        }
    });

    $('#saveRequestButton').click(function(e){
        if(!$('#inventoryItemsField').val()){
            swal("No records", "Please select some records.", "error");
            e.preventDefault();
        }
    });

});