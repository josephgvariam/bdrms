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

    var Box = Backbone.Model.extend({
        defaults: {
            barcode: '',
            location: ''
        }
    });

    var Boxes = Backbone.Collection.extend({
        model: Box
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
                if(this.options.nextViewFunctionArgs){
                    this.options.nextViewFunction.apply(this.options.rootView, this.options.nextViewFunctionArgs);
                }
                else {
                    this.options.nextViewFunction.apply(this.options.rootView, [this.model]);
                }
            }
        },

        handleSaveError: function(model, response)
        {
            if(response.responseJSON.message) {
                this.showAlert([response.responseJSON.message], 'danger');
            }
        },

    });

    var VerifyRecordsRowView = Marionette.View.extend({
        tagName: 'li',
        className: function(){
            return 'list-group-item list-group-item-' + (this.model.get('verified') ? 'success' : 'danger');
        },
        template:'#verify-records-row-template',

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

    var EmptyVerifyRecordsRowView = Marionette.View.extend({
        template: _.template('No Records.')
    });

    var VerifyRecordsListView = Marionette.CollectionView.extend({
        tagName: 'ul',
        className: 'list-group recordlist',
        childView: VerifyRecordsRowView,
        emptyView: EmptyVerifyRecordsRowView,

        childViewOptions: function(){
            return {
                isSystemRecordsView: this.options.isSystemRecordsView
            };
        },

        onChildviewDeleteScannedRecord: function(childView) {
            this.triggerMethod('delete:scanned:record', childView.model);
        }

    });

    var VerifyRecordsView = Marionette.View.extend({
        template: '#verify-records-template',

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
            this.showChildView('scannedRecordsRegion', new VerifyRecordsListView({collection: this.scannedRecords, isSystemRecordsView: false}));
            this.showChildView('systemRecordsRegion', new VerifyRecordsListView({collection: this.systemRecords, isSystemRecordsView: true}));
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


    var StoreRecordsRowView = Marionette.View.extend({
        tagName: 'li',
        className: 'list-group-item list-group-item-info',
        template:'#store-records-row-template',

        templateContext: function(){
            return {
                isStoredBoxesView: this.isStoredBoxesView
            };
        },

        initialize: function(options){
            this.isStoredBoxesView = options.isStoredBoxesView;
        },

        triggers: {
            'click .deleteStoredBox': 'delete:stored:box'
        }
    });

    var EmptyStoreRecordsRowView = Marionette.View.extend({
        template: _.template('No Boxes. Start scanning boxes & shelves.')
    });

    var StoreRecordsListView = Marionette.CollectionView.extend({
        tagName: 'ul',
        className: 'list-group recordlist',
        childView: StoreRecordsRowView,
        emptyView: EmptyStoreRecordsRowView,

        childViewOptions: function(){
            return {
                isStoredBoxesView: this.options.isStoredBoxesView
            };
        },

        onChildviewDeleteStoredBox: function(childView) {
            this.triggerMethod('delete:stored:box', childView.model);
        }


    });

    var StoreRecordsView = Marionette.View.extend({
        template: '#store-records-template',

        events: {
            'change #validatedBoxBarcode' : 'storeBox',
        },


        focusBack: function(){
            this.$('#validatedBoxBarcode').val('');
            this.$('#validatedBoxBarcode').focus();
        },

        regions: {
            validatedBoxesRegion: {el: '#validatedBoxesRegion', replaceElement: true},
            storedBoxesRegion: {el: '#storedBoxesRegion', replaceElement: true}
        },

        initialize: function(){
            _.bindAll(this, "handleSaveSuccess", "handleSaveError", "updateRequest");

            this.validatedBoxes = new Boxes();
            this.storedBoxes = new Boxes();
            this.resetLists();
        },

        resetLists: function(){

            this.validatedBoxes.reset();
            this.storedBoxes.reset();

            var storageType = this.model.get('storageType').name;

            var box;
            _.each(this.model.get('inventoryItems'), function(inventoryItem){
                if(storageType === 'BOX'){
                    box = new Box(inventoryItem.box);
                }else if(storageType === 'FILE'){
                    box = new Box(inventoryItem.file.box);
                }else{
                    box = new Box(inventoryItem.document.file.box);
                }

                this.validatedBoxes.add(box);
            }, this);

        },

        storeBox: function(e){
            var barcode = this.$('#validatedBoxBarcode').val().trim().toUpperCase();
            this.$('#validatedBoxBarcode').val('');

            if(barcode) {
                var validatedBox = this.validatedBoxes.findWhere({barcode: barcode});

                var _this = this;
                if (typeof validatedBox !== 'undefined') {
                    swal({
                        title: "Scan Shelf Barcode",
                        text: "",
                        type: "input",
                        showCancelButton: true,
                        closeOnConfirm: this.validatedBoxes.size()!==1,
                        inputPlaceholder: "Shelf Barcode"
                    }, function (inputValue) {
                        if (inputValue === false) return false;
                        if (inputValue === "") {
                            swal.showInputError("Scan shelf!");
                            return false;
                        }

                        validatedBox.set('shelf', {barcode: inputValue});
                        validatedBox.set('location', inputValue );

                        _this.storedBoxes.add(validatedBox);
                        _this.validatedBoxes.remove(validatedBox);

                        _this.updateStoreProgress();
                    });
                }
                else{
                    swal({
                        title: 'Not a valid box!',
                        text: 'Barcode is not part of this request.',
                        type: 'error'
                    });
                }
            }
        },

        updateStoreProgress: function(){

            var validatedBoxesSize = this.validatedBoxes.size();
            var storedBoxesSize = this.storedBoxes.size();

            var p = parseInt((storedBoxesSize / (validatedBoxesSize + storedBoxesSize)) * 100);

            var pb = this.$('#storeProgressbar');
            pb.width(p + '%');
            pb.text(p + '%');

            if(validatedBoxesSize === 0){
                this.updateRequest();
            }
        },

        updateRequest: function(){
            this.storedBoxes.url = '/api/boxes/batch';
            this.storedBoxes.sync('update', this.storedBoxes, {
                success: this.handleSaveSuccess,
                error: this.handleSaveError
            });
        },

        getLocation: function(boxBarcode){
            var box = this.storedBoxes.findWhere({barcode: boxBarcode});
            return box.get('location');
        },

        getFacility: function(boxes, boxBarcode){
            for (var i = 0; i < boxes.length; i++) {
                if(boxes[i].barcode === boxBarcode){
                    return boxes[i].shelf.facility;
                }
            }
        },

        handleSaveSuccess: function(boxes){
            var storageType = this.model.get('storageType').name;

            _.each(this.model.get('inventoryItems'), function(inventoryItem){
                inventoryItem.status = 'STORED';

                if(storageType === 'BOX'){
                    inventoryItem.box.location = this.getLocation(inventoryItem.box.barcode);
                    inventoryItem.facility = this.getFacility(boxes, inventoryItem.box.barcode);
                }else if (storageType === 'FILE'){
                    inventoryItem.file.location = this.getLocation(inventoryItem.file.box.barcode);
                    inventoryItem.facility = this.getFacility(boxes, inventoryItem.file.box.barcode);
                }else{
                    inventoryItem.document.location = this.getLocation(inventoryItem.document.file.box.barcode);
                    inventoryItem.facility = this.getFacility(boxes, inventoryItem.document.file.box.barcode);
                }
            }, this);

            this.model.save({
                status: 'STORED'
            }, {
                wait: true,
                success: this.handleSaveSuccess2,
                error: this.handleSaveError2
            });

        },

        handleSaveSuccess2: function(model, response){
            swal({
                    title: 'Request Updated!',
                    text: 'Records are now stored.',
                    type: "success"
                },
                function(){
                    window.location.href='/requests/' + response.id ;
                });
        },

        handleSaveError2: function(model, response){
            if(response.responseJSON.message) {
                swal.close();
                util.showAlert([response.responseJSON.message], 'danger');
            }
        },

        handleSaveError: function(response){
            //console.log('error', response);
            swal.close();
            this.resetLists();
            this.updateStoreProgress();

            if(response.responseJSON && response.responseJSON.message){
                util.showAlert('Error saving boxes', response.responseJSON.message, 'danger');
            }
            else{
                util.showAlert('Error saving boxes', response, 'danger');
            }

        },

        onChildviewDeleteStoredBox: function(sBox) {
            this.storedBoxes.remove(sBox);
            this.validatedBoxes.add(sBox);

            this.updateStoreProgress();
        },


        onRender: function() {
            this.updateStoreProgress();
            this.showChildView('validatedBoxesRegion', new StoreRecordsListView({collection: this.validatedBoxes, isStoredBoxesView: false}));
            this.showChildView('storedBoxesRegion', new StoreRecordsListView({collection: this.storedBoxes, isStoredBoxesView: true}));
        },

        onAttach: function(){
            this.$('#validatedBoxBarcode').focus();
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

        showBeforeProcessRequestView: function(request, title, msg, requestNextStatus, inventoryItemNextStatus, nextViewFunction, navText, nextViewFunctionArgs){
            this.showChildView('main', new BeforeProcessRequestView({model: request, rootView: this, title: title, msg: msg, requestNextStatus: requestNextStatus, inventoryItemNextStatus: inventoryItemNextStatus, nextViewFunction: nextViewFunction, navText: navText, nextViewFunctionArgs: nextViewFunctionArgs}));
        },

        showVerifyRecordsView: function (request) {
            this.showChildView('main', new VerifyRecordsView({model: request, rootView: this, inventoryItemNextStatus: 'FETCHED', requestNextStatus: 'FETCHED', title: 'Fetch Records'}));
        },

        showUpdateLocationView: function (request) {
            this.showChildView('main', new UpdateLocationView({model: request, rootView: this}));
        },

        showAssignRetrievalDeskUserView: function (request) {
            this.showChildView('main', new AssignUserView({model: request, rootView: this, title: 'Assign Retrieval Desk User', label: 'Retrieval Desk User', requestNextStatus: 'ASSIGNED_RETRIEVAL_DESK'}));
        },

        showRetrievalDeskVerifyRecordsView: function (request) {
            this.showChildView('main', new VerifyRecordsView({model: request, rootView: this, inventoryItemNextStatus: null, requestNextStatus: 'ASSIGNED_LOGISTICS_DESK', title: 'Validate Records'}));
        },

        showAssignDeliveryUserView: function (request) {
            this.showChildView('main', new AssignUserView({model: request, rootView: this, title: 'Assign Delivery User', label: 'Delivery User', requestNextStatus: 'ASSIGNED_DELIVERY'}));
        },

        showVerifyIncomingRecordsView: function (request) {
            this.showChildView('main', new VerifyRecordsView({model: request, rootView: this, inventoryItemNextStatus: 'VALIDATED', requestNextStatus: 'VALIDATED', title: 'Validate Incoming Records'}));
        },

        showStoreRecordsView: function (request) {
            this.showChildView('main', new StoreRecordsView({model: request, rootView: this}));
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

            if(type === 'TRANSFER') {
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
                    nextViewFunctionArgs = [request, 'Verify Incoming Records', 'Proceed with verifying the incoming records for this request?', 'INCOMING', 'INCOMING', rootView.showVerifyIncomingRecordsView, 'verifyIncomingRecords'];
                    rootView.showBeforeProcessRequestView(request, 'Proceed', 'Proceed with this request?', 'TRANSIT', 'TRANSIT', rootView.showBeforeProcessRequestView, 'transitRecords', nextViewFunctionArgs);
                }
                else if (status === 'TRANSIT'){
                    rootView.showBeforeProcessRequestView(request, 'Verify Incoming Records', 'Proceed with verifying the incoming records for this request?', 'INCOMING', 'INCOMING', rootView.showVerifyIncomingRecordsView, 'verifyIncomingRecords');
                }
                else if (status === 'INCOMING'){
                    rootView.showVerifyIncomingRecordsView(request);
                }
                else if (status === 'VALIDATED' ){
                    rootView.showStoreRecordsView(request);
                }
                else if (status === 'STORED' ){
                    rootView.showBeforeProcessRequestView(request, 'Close Request', 'Proceed with closing this request?', 'CLOSED', rootView.showCloseRequestView, '');
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