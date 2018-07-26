(function() {

    var Request = Backbone.Model.extend({
        urlRoot: function (){
            if(this.get('type')){
                return '/api/' + this.get('type').toLowerCase() + 'requests';
            }
            return '/api/requests';
        },

        initialize: function(){
            this.listenTo(this, 'request', this.requestStart);
            this.listenTo(this, 'sync', this.requestSuccess);
            this.listenTo(this, 'error', this.requestError);
            this.spinner = new Spinner();
        },

        startSpinner: function(){
            this.spinner.spin(document.getElementById('loading'));
        },

        stopSpinner: function(e){
            this.spinner.stop();
        },

        requestStart: function(model, xhr, options){
            this.startSpinner();
        },

        requestSuccess: function(model, response, options){
            this.stopSpinner();
        },

        requestError: function(model, response, options){
            this.stopSpinner();
        }
    });

    var VerifyRecord = Backbone.Model.extend({
        defaults: {
            verified: false
        }
    });

    var VerifyRecords = Backbone.Collection.extend({
        model: VerifyRecord
    });

    var AssignUserView = Marionette.View.extend({
        template: '#assign-user-template',

        templateContext: function(){
            return {
                title: this.options.title,
                label: this.options.label
            };
        },

        initialize: function(){
            _.bindAll(this, "handleSaveSuccess", "handleSaveError");
        },

        events: {
            'click #save-button': 'handleSave'
        },

        onRender: function(){
            this.$('.dropdown-select-ajax').select2({debug : false, theme : 'bootstrap', allowClear : true});
        },

        handleSave: function(e){
            e.preventDefault();

            if(!this.$('#userAssigned').val()){
                this.showValidationError('userAssigned', 'may not be null');
                return;
            }

            this.model.save({
                    userAssigned: {id: this.$('#userAssigned').val().trim()},
                    status: this.options.requestNextStatus
                },
                {
                    wait: true,
                    success: this.handleSaveSuccess,
                    error: this.handleSaveError
                });

        },

        handleSaveSuccess: function(model, response)
        {
            swal({
                title: 'Request Updated!',
                text: 'User has been assigned successfully.',
                type: 'success'

            },function(){
                window.location.href='/requests/' + response.id;
            });
        },

        handleSaveError: function(model, response){
            console.log(model, response);
            if(response.responseJSON) {
                _.each(response.responseJSON.errors, function (val, key) {
                    this.showValidationError(key, val);
                });
            }
        },

        clearValidationErrors: function(){
            this.$('.form-group').removeClass('has-error has-feedback');
            this.$('.help-block').remove();
        },

        showValidationError: function (key, val) {
            var $key = this.$('#'+key);
            $key.closest('.form-group').addClass('has-error has-feedback');
            $key.closest('div').append($('<span id="'+key+'-error" class="help-block">'+val+'</span>'));
        }

    });


    var BeforeProcessRequestView = Marionette.View.extend({
        template: '#before-process-request-template',

        templateContext: function() {
            return {
                msg: this.options.msg,
                title: this.options.title
            };
        },

        initialize: function(){
            _.bindAll(this, "handleSaveSuccess", "handleSaveError");
        },

        events: {
            'click #startProcessRequestButton': 'start',
            'click #cancelProcessRequestButton': 'cancel'
        },

        start: function(e){
            e.preventDefault();

            if(this.options.inventoryItemNextStatus) {
                _.each(this.model.get('inventoryItems'), function (inventoryItem) {
                    inventoryItem.status = this.options.inventoryItemNextStatus;
                }, this);
            }

            this.model.save({'status': this.options.requestNextStatus}, {wait: true, success: this.handleSaveSuccess, error: this.handleSaveError});
        },

        cancel: function(e){
            e.preventDefault();
            window.location.href='/requests/';
        },

        handleSaveSuccess: function(model, response)
        {
            Backbone.history.navigate(response.id + '/workflow/' + this.options.navText, {trigger: false});

            if(this.options.nextViewFunction) {
                this.options.nextViewFunction.apply(this.options.rootView, [this.model]);
            }
        },

        handleSaveError: function(model, response)
        {
            if(response.responseJSON.message) {
                this.showAlert([response.responseJSON.message], 'danger');
            }
        },

    });

    var RetrieveRecordsRowView = Marionette.View.extend({
        tagName: 'li',
        className: function(){
            return 'list-group-item list-group-item-' + (this.model.get('verified') ? 'success' : 'danger');
        },
        template:'#retrieve-records-row-template',

        templateContext: function(){
            return {
                isSystemRecordsView: this.isSystemRecordsView
            };
        },

        initialize: function(options){
            this.isSystemRecordsView = options.isSystemRecordsView;
        },

        triggers: {
            'click .deleteScannedRecord': 'delete:scanned:record'
        }

    });

    var EmptyRetrieveRecordsRowView = Marionette.View.extend({
        template: _.template('No Records.')
    });

    var RetrieveRecordsListView = Marionette.CollectionView.extend({
        tagName: 'ul',
        className: 'list-group recordlist',
        childView: RetrieveRecordsRowView,
        emptyView: EmptyRetrieveRecordsRowView,

        childViewOptions: function(){
            return {
                isSystemRecordsView: this.options.isSystemRecordsView
            };
        },

        onChildviewDeleteScannedRecord: function(childView) {
            this.triggerMethod('delete:scanned:record', childView.model);
        }

    });

    var RetrieveRecordsView = Marionette.View.extend({
        template: '#retrieve-records-template',

        templateContext: function(){
            return {
                title: this.options.title
            };
        },

        events: {
            'change #recordBarcode' : 'addScanned',
            'blur #recordBarcode': 'focusBack'
        },

        focusBack: function(){
            this.$('#recordBarcode').val('');
            this.$('#recordBarcode').focus();
        },

        regions: {
            scannedRecordsRegion: {el: '#scannedRecordsRegion', replaceElement: true},
            systemRecordsRegion: {el: '#systemRecordsRegion', replaceElement: true}
        },

        initialize: function(){
            _.bindAll(this, "handleSaveSuccess", "handleSaveError", "updateRequest");

            this.systemRecords = new VerifyRecords();

            _.each(this.model.get('inventoryItems'), function(inventoryItem){
                var barcode = '';

                if(inventoryItem.type === 'BOX'){
                    barcode = inventoryItem.boxBarcode;
                }
                else if(inventoryItem.type === 'FILE'){
                    barcode = inventoryItem.fileBarcode;
                }
                else {
                    barcode = inventoryItem.documentBarcode;
                }

                var vRecord = new VerifyRecord({
                    id: barcode,
                    type: inventoryItem.type,
                    boxBarcode: inventoryItem.boxBarcode,
                    fileBarcode: inventoryItem.fileBarcode,
                    documentBarcode: inventoryItem.documentBarcode,
                    shelfBarcode: inventoryItem.shelfBarcode
                });

                this.systemRecords.add(vRecord);
            }, this);

            this.scannedRecords = new VerifyRecords();
        },

        addScanned: function(e){
            var barcode = this.$('#recordBarcode').val().toUpperCase().trim();

            this.$('#recordBarcode').val('');
            this.$('#recordBarcode').focus();

            if(barcode) {

                var sysRecord = this.systemRecords.get(barcode);
                var verified = typeof sysRecord !== 'undefined';

                if (verified === true) {
                    sysRecord.set('verified', true);

                    this.systemRecords.add(sysRecord);
                    this.systemRecords.trigger('reset');
                }

                var scanRecord = new VerifyRecord({id: barcode, verified: verified});
                this.scannedRecords.add(scanRecord);

                this.updateVerifyProgress();
            }
        },

        updateRequest: function(){
            if(this.options.inventoryItemNextStatus) {
                _.each(this.model.get('inventoryItems'), function (inventoryItem) {
                    inventoryItem.status = this.options.inventoryItemNextStatus;
                }, this);
            }

            this.model.save({
                status: this.options.requestNextStatus
            }, {
                wait: true,
                success: this.handleSaveSuccess,
                error: this.handleSaveError
            });
        },

        handleSaveSuccess: function(model, response){
            swal({
                    title: 'Request Updated!',
                    text: 'Records are now validated.',
                    type: "success"
                },
                function(){
                    window.location.href='/requests/' + response.id ;
                });
        },

        handleSaveError: function(model, response, options){
            if(response.responseJSON.message) {
                swal.close();
                this.showAlert([response.responseJSON.message], 'danger');
            }
        },

        updateVerifyProgress: function(){
            var sysVerified = this.systemRecords.where({verified: true});
            var sysSize = this.systemRecords.size();

            var scanVerified = this.scannedRecords.where({verified: true});
            var scanSize = this.scannedRecords.size();

            var p = parseInt((sysVerified.length / sysSize) * 100);

            var pb = this.$('#recordsProgressbar');
            pb.width(p + '%');
            pb.text(p + '%');


            if(sysVerified.length === sysSize && scanVerified.length === scanSize && sysSize === scanSize){
                swal({
                        title: 'All Records Verified!',
                        text: '',
                        type: "success",
                        closeOnConfirm: false
                    },
                    this.updateRequest);
            }
        },

        onRender: function() {
            this.updateVerifyProgress();
            this.showChildView('scannedRecordsRegion', new RetrieveRecordsListView({collection: this.scannedRecords, isSystemRecordsView: false}));
            this.showChildView('systemRecordsRegion', new RetrieveRecordsListView({collection: this.systemRecords, isSystemRecordsView: true}));
        },

        onChildviewDeleteScannedRecord: function(scanRecord) {
            var barcode = scanRecord.id;
            this.scannedRecords.remove(scanRecord);

            var sysRecord = this.systemRecords.get(barcode);

            if (typeof sysRecord !== 'undefined') {
                sysRecord.set('verified', false);

                this.systemRecords.trigger('reset');
            }

            this.updateVerifyProgress();
        },

        onAttach: function(){
            this.$('#recordBarcode').focus();
        }
    });

    var UpdateLocationView = Marionette.View.extend({
        template: '#update-location-template',

        events: {
            'click #save-button': 'handleSave'
        },


        handleSave: function(e){
            e.preventDefault();

            if(!this.$('#locationBarcode').val()){
                this.showValidationError('locationBarcode', 'may not be empty');
                return;
            }

            var data = {
                requestId: this.model.get('id'),
                locationBarcode: this.$('#locationBarcode').val()
            };

            $.ajax({
                type: 'POST',
                url: '/api/requests/updateLocation',
                data: data,
                success: this.handleSaveSuccess,
                error: this.handleSaveError
            });

        },

        handleSaveSuccess: function(data, status, jqXHR)
        {
            swal({
                title: 'Request Updated!',
                text: 'Location has been updated successfully.',
                type: 'success'

            },function(){
                window.location.href='/requests/' + data.id;
            });
        },

        handleSaveError: function(jqXHR, status, errorMsg){
            console.log(jqXHR, status, errorMsg);
        },

        clearValidationErrors: function(){
            this.$('.form-group').removeClass('has-error has-feedback');
            this.$('.help-block').remove();
        },

        showValidationError: function (key, val) {
            var $key = this.$('#'+key);
            $key.closest('.form-group').addClass('has-error has-feedback');
            $key.closest('div').append($('<span id="'+key+'-error" class="help-block">'+val+'</span>'));
        }

    });

    var DeliverySignoffView = Marionette.View.extend({
        template: '#delivery-signoff-template',

        initialize: function(){
            _.bindAll(this, "handleSaveSuccess", "handleSaveError");
        },

        events: {
            'click #save-button': 'handleSave'
        },

        handleSave: function(e){
            e.preventDefault();

            if(!this.$('#clientOtp').val()){
                this.showValidationError('clientOtp', 'may not be empty');
                return;
            }

            _.each(this.model.get('inventoryItems'), function (inventoryItem) {
                inventoryItem.status = 'ATCLIENT';
            }, this);

            this.model.save({
                    status: 'DELIVERED'
                },
                {
                    wait: true,
                    success: this.handleSaveSuccess,
                    error: this.handleSaveError
                });
        },

        handleSaveSuccess: function(data, status, jqXHR)
        {
            swal({
                title: 'Request Updated!',
                text: 'Request been updated successfully.',
                type: 'success'

            },function(){
                window.location.href='/requests/' + data.id;
            });
        },

        handleSaveError: function(jqXHR, status, errorMsg){
            console.log(jqXHR, status, errorMsg);
        },

        clearValidationErrors: function(){
            this.$('.form-group').removeClass('has-error has-feedback');
            this.$('.help-block').remove();
        },

        showValidationError: function (key, val) {
            var $key = this.$('#'+key);
            $key.closest('.form-group').addClass('has-error has-feedback');
            $key.closest('div').append($('<span id="'+key+'-error" class="help-block">'+val+'</span>'));
        }

    });


    var CloseRequestView = Marionette.View.extend({
        template: '#close-request-template',

        events: {
            'click #cancelButton': 'cancel'
        },

        cancel: function(e){
            e.preventDefault();
            window.location.href='/requests/';
        },
    });

    var RootView = Marionette.View.extend({
        template: _.template('<div id="main"></div>'),

        regions: {
            main: '#main'
        },

        showAssignUserView: function (request) {
            this.showChildView('main', new AssignUserView({model: request, rootView: this, title: 'Assign User', label: 'User', requestNextStatus: 'ASSIGNED'}));
        },

        showBeforeProcessRequestView: function(request, title, msg, requestNextStatus, inventoryItemNextStatus, nextViewFunction, navText){
            this.showChildView('main', new BeforeProcessRequestView({model: request, rootView: this, title: title, msg: msg, requestNextStatus: requestNextStatus, inventoryItemNextStatus: inventoryItemNextStatus, nextViewFunction: nextViewFunction, navText: navText}));
        },

        showVerifyRecordsView: function (request) {
            this.showChildView('main', new RetrieveRecordsView({model: request, rootView: this, inventoryItemNextStatus: 'FETCHED', requestNextStatus: 'FETCHED', title: 'Retrieve Records'}));
        },

        showUpdateLocationView: function (request) {
            this.showChildView('main', new UpdateLocationView({model: request, rootView: this}));
        },

        showAssignRetrievalDeskUserView: function (request) {
            this.showChildView('main', new AssignUserView({model: request, rootView: this, title: 'Assign Retrieval Desk User', label: 'Retrieval Desk User', requestNextStatus: 'ASSIGNED_RETRIEVAL_DESK'}));
        },

        showRetrievalDeskVerifyRecordsView: function (request) {
            this.showChildView('main', new RetrieveRecordsView({model: request, rootView: this, inventoryItemNextStatus: null, requestNextStatus: 'ASSIGNED_LOGISTICS_DESK', title: 'Validate Records'}));
        },

        showAssignDeliveryUserView: function (request) {
            this.showChildView('main', new AssignUserView({model: request, rootView: this, title: 'Assign Delivery User', label: 'Delivery User', requestNextStatus: 'ASSIGNED_DELIVERY'}));
        },

        showDeliverySignoffView: function (request) {
            this.showChildView('main', new DeliverySignoffView({model: request, rootView: this}));
        },

        showCloseRequestView: function (request) {
            this.showChildView('main', new CloseRequestView({model: request, rootView: this}));
        },
    });


    var Controller = Marionette.Object.extend({
        start: function(requestId, splat) {
            var request = new Request({id: requestId});
            this.listenToOnce(request, 'sync', this.workflow);
            request.fetch();
        },

        workflow: function(request){
            var rootView = this.getOption('rootView');

            var type = request.get('type');
            var status = request.get('status');

            if(type === 'RETRIEVAL') {
                if (status === 'OPEN') {
                    rootView.showAssignUserView(request);
                }
                else if (status === 'ASSIGNED') {
                    rootView.showBeforeProcessRequestView(request, 'Proceed', 'Proceed with this request?', 'INPROGRESS', null,  rootView.showVerifyRecordsView, 'verifyRecords');
                }
                else if (status === 'INPROGRESS') {
                    rootView.showVerifyRecordsView(request);
                }
                else if (status === 'FETCHED' ){
                    rootView.showUpdateLocationView(request);
                }
                else if (status === 'PACKED' ){
                    rootView.showAssignRetrievalDeskUserView(request);
                }
                else if (status === 'ASSIGNED_RETRIEVAL_DESK' ){
                    rootView.showRetrievalDeskVerifyRecordsView(request);
                }
                else if (status === 'ASSIGNED_LOGISTICS_DESK' ){
                    rootView.showAssignDeliveryUserView(request);
                }
                else if (status === 'ASSIGNED_DELIVERY' ){
                    rootView.showBeforeProcessRequestView(request, 'Proceed', 'Proceed with this request?', 'TRANSIT', 'TRANSIT',  rootView.showDeliverySignoffView, 'deliverySignoff');
                }
                else if (status === 'TRANSIT' ){
                    rootView.showDeliverySignoffView(request);
                }
                else if (status === 'DELIVERED' ){
                    rootView.showBeforeProcessRequestView(request, 'Close Request', 'Proceed with closing this request?', 'CLOSED', null, rootView.showCloseRequestView, '');
                }
                else if (status === 'CLOSED' ){
                    rootView.showCloseRequestView(request);
                }
            }

        }

    });


    var Router = Marionette.AppRouter.extend({
        initialize: function(options) {
            this.controller = new Controller(options);
        },

        appRoutes: {
            ':requestId/workflow': 'start',
            ':requestId/workflow/*splat': 'start'
        }
    });


    var App = Marionette.Application.extend({
        region: '#app',

        onStart: function() {
            var rootView = new RootView();
            var router = new Router({rootView: rootView});
            this.showView(rootView);

            Backbone.history.start({ pushState: true, root: '/requests' });
        }
    });

    var app = new App();
    app.start();

})();