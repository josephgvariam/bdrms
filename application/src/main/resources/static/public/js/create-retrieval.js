(function() {

    var InventoryItem = Backbone.Model.extend({
        defaults: {
            ref1: '',
            ref2: '',
            ref3: '',
            ref4: '',
            ref5: '',
            status: 'NOTSTORED',
            type: null
        }
    });

    var InventoryItems = Backbone.Collection.extend({
        model: InventoryItem
    });

    var inventoryItems = new InventoryItems();

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

            inventoryItems.reset();
            _.each(rowsSelected, function(row){
                var inventoryItem = new InventoryItem(row);
                inventoryItems.add(inventoryItem);
            }, this);

            $('#recordsModal').modal('hide');

        },

        showRecordsModal: function(e){
            e.preventDefault();

            var storageTypeSelection = $('#storagetypesselect2').select2('data')[0];
            if(storageTypeSelection){
                var storageType = storageTypeSelection.text;

                $('#recordsModal').modal('show');

                var datatable = $('#recordsDataTable').DataTable( {
                    retrieve: true,
                    searching: false,
                    ordering: false,
                    ajax : {
                        url : "/api/inventoryitems",
                        dataSrc : function (json) {
                            return json.content;
                        }
                    },
                    columns: [
                        { data: "id" },
                        { data: "id" },
                        { data: "boxBarcode" },
                        { data: "fileBarcode" },
                        { data: "documentBarcode" },
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
                } );

                $('#recordsModal').on('shown.bs.modal', function() {
                    var dataTable = $('#recordsDataTable').DataTable();
                    dataTable.columns.adjust();
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

})();