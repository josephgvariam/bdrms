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

    var InventoryItem = Backbone.Model.extend({
        defaults: {
            ref1: '',
            ref2: '',
            ref3: '',
            ref4: '',
            ref5: '',
            status: 'NOTSTORED',
            type: null,
            item: null
        }
    });

    var InventoryItems = Backbone.Collection.extend({
        model: InventoryItem
    });

    var Box = Backbone.Model.extend({
        defaults: {
            barcode: '',
            inventoryItem: null,
            storageType: '',
            location: ''
        }
    });

    var File = Backbone.Model.extend({
        defaults: {
            barcode: '',
            inventoryItem: null,
            box: null,
            storageType: '',
            location: ''
        }
    });

    var Document = Backbone.Model.extend({
        defaults: {
            barcode: '',
            inventoryItem: null,
            file: null,
            storageType: '',
            location: ''
        }
    });

    var Boxes = Backbone.Collection.extend({
        model: Box
    });

    var Files = Backbone.Collection.extend({
        model: Box
    });

    var Documents = Backbone.Collection.extend({
        model: Box
    });

//////////////////////// VIEWS


    var BoxRowView = Marionette.View.extend({
        tagName: 'tr',
        template: '#box-row-template',
    });

    var EmptyBoxRowView = Mn.View.extend({
        template: _.template('No Boxes. Start adding boxes.')
    });

    var BoxesTableView = Marionette.CollectionView.extend({
        tagName: 'tbody',
        childView: BoxRowView,
        emptyView: EmptyBoxRowView,
    });

    var BoxesView = Marionette.View.extend({
        template: '#boxes-template',

        regions: {
            body: {
                el: 'tbody',
                replaceElement: true
            }
        },

        initialize: function() {
            // this.boxes = new Boxes();
            // //this.idCount = 1;
        },

        events: {
            'click #addBoxButton': 'addBox',
            'click #deleteBoxButton': 'deleteBox',
            'click div.ckbox>input': 'updateDeleteButtonEnabled',
            'click #editBoxButton': 'editBox'
        },

        updateDeleteButtonEnabled: function() {
            if($('div.ckbox>input:checked').length){
                $('#deleteBoxButton').prop('disabled', false);
            }
            else{
                $('#deleteBoxButton').prop('disabled', true);
            }
        },

        editBox: function(e){
            var barcode = String($(e.currentTarget).data('barcode'));
            var box = this.collection.findWhere({barcode: barcode})
            this.showBox(box);
        },

        showBox: function(box){
            this.boxToShow = box;
            this.triggerMethod('show:box', this);
        },

        addBox: function(e) {
            var storageType = this.options.request.get('storageType').name;
            var box = new Box({storageType: storageType});

            if(storageType === 'BOX'){
                box.set('inventoryItem', new InventoryItem({type: 'BOX', item: box}));
            }

            this.showBox(box);
        },

        deleteBox: function(e) {
            var checked = $('div.ckbox>input:checked');

            _.each(checked, function (box) {
                var barcode = String($(box).data('barcode'));

                var box = this.collection.findWhere({barcode: barcode})

                this.collection.remove(box);
            }, this);

            this.updateDeleteButtonEnabled();
        },

        onRender() {
            this.showChildView('body', new BoxesTableView({collection: this.collection}));
        }
    });

    var BoxView = Marionette.View.extend({
        template: '#box-template',

        initialize: function(){
            this.isNewBox = this.model.get('barcode') === '';
        },

        events: {
            'click #ok-button': 'handleOkButton',
            'click #cancel-button': 'handleCancelButton',
        },

        handleOkButton: function(e){
            e.preventDefault();

            this.clearValidationErrors();

            var barcode = this.$('#barcode').val();
            var location = this.$('#location').val();

            if(!barcode){
                this.showValidationError('barcode', 'may not be empty');
                return;
            }

            if(this.isNewBox && this.collection.findWhere({barcode: barcode})){
                this.showValidationError('barcode', 'barcode already exists');
                return;
            }

            this.model.set({
                'barcode': barcode,
                'location': location,
            });

            if(this.model.get('storageType') === 'BOX'){

                var inventoryItem = this.model.get('inventoryItem');
                inventoryItem.set('ref1', this.$('#ref1').val());
                inventoryItem.set('ref2', this.$('#ref2').val());
                inventoryItem.set('ref3', this.$('#ref3').val());
                inventoryItem.set('ref4', this.$('#ref4').val());
                inventoryItem.set('ref5', this.$('#ref5').val());

                this.model.set('inventoryItem', inventoryItem);

            }

            this.showBoxesView();
        },

        handleCancelButton: function(e){
            e.preventDefault();

            this.model = null;
            this.showBoxesView();
        },

        showBoxesView: function() {
            this.triggerMethod('show:boxes', this);
        },

        clearValidationErrors(){
            this.$('.form-group').removeClass('has-error has-feedback');
            this.$('.help-block').remove();
        },

        showValidationError: function (key, val) {
            $key = this.$('#'+key);
            $key.closest('.form-group').addClass('has-error has-feedback');
            $key.closest('div').append($('<span id="'+key+'-error" class="help-block">'+val+'</span>'));
        },

        onAttach: function(){
            this.$('#barcode').focus();
        }

    });

    var AddNewRecordsView = Marionette.View.extend({
        template: '#add-new-record-template',

        regions: {
            body: {el: '#add-new-record', replaceElement: true}
        },

        initialize: function(){
            this.boxes = new Boxes();
            this.boxes.add(new Box({barcode: '123'}));
            this.boxes.add(new Box({barcode: '456'}));
            this.boxes.add(new Box({barcode: '789'}));
        },

        onRender: function() {
            this.showBoxesView();
        },

        onChildviewShowBox: function(childView) {
            this.showChildView('body', new BoxView({model: childView.boxToShow, collection: this.boxes}));
        },

        onChildviewShowBoxes: function(childView) {
            if(childView.model){
                this.boxes.add(childView.model, {merge: !childView.isNewBox});
                console.log(this.boxes);
            }

            //console.log('boxes: ', this.boxes);
            this.showBoxesView();
        },

        showBoxesView: function(){
            this.showChildView('body', new BoxesView({collection: this.boxes, request: this.model}));
        }
    });


    var AssignUserView = Marionette.View.extend({
        template: '#assign-user-template',

        initialize: function(){
            _.bindAll(this, "handleSaveSuccess", "handleSaveError");
        },

        events: {
            'click #save-button': 'handleSave'
        },

        onRender: function(){
            this.$('.dropdown-select-ajax').select2({debug : false, theme : 'bootstrap', allowClear : true,});
        },

        handleSave: function(e){
            e.preventDefault();

            if(!this.$('#userAssigned').val()){
                this.showValidationError('userAssigned', 'may not be null');
                return;
            }

            this.model.save({
                    userAssigned: {id: this.$('#userAssigned').val()},
                    status: 'ASSIGNED'
                },
                {
                    success: this.handleSaveSuccess,
                    error: this.handleSaveError
                });

        },

        handleSaveSuccess: function(model, response)
        {
            //console.log('success', response);
            //Backbone.history.navigate("/" + response.id, {trigger: true});
            this.options.rootView.showInfoModal('Info', 'User has been assigned successfully.', function(){window.location.href='/requests/' + response.id});
        },

        handleSaveError: function(model, response){
            //console.log('error', response);

            if(response.responseJSON) {
                _.each(response.responseJSON.errors, function (val, key) {
                    this.showValidationError(key, val);
                });
            }
        },

        clearValidationErrors(){
            this.$('.form-group').removeClass('has-error has-feedback');
            this.$('.help-block').remove();
        },

        showValidationError: function (key, val) {
            $key = this.$('#'+key);
            $key.closest('.form-group').addClass('has-error has-feedback');
            $key.closest('div').append($('<span id="'+key+'-error" class="help-block">'+val+'</span>'));
        }

    });

    var RootView = Marionette.View.extend({
        template: _.template('<div id="main"></div>'),

        regions: {
            main: '#main'
        },

        showAssignUserView: function (request) {
            this.showChildView('main', new AssignUserView({model: request, rootView: this}));
        },

        showAddNewRecordView: function (request) {
            this.showChildView('main', new AddNewRecordsView({model: request, rootView: this}));
        },

        showInfoModal: function(title, message, onHideFunction){
            var modal = $('#infoModal');
            modal.find('.modal-title').text(title);
            modal.find('.modal-body').html('<p>' + message + '</p>');

            if(onHideFunction) {
                modal.on('hidden.bs.modal', onHideFunction);
            }

            modal.modal();
        }
    });

    var Controller = Marionette.Object.extend({
        start: function(requestId) {
            var request = new Request({id: requestId});
            this.listenTo(request, 'sync', this.workflow);
            request.fetch();
        },

        workflow: function(request){
            var rootView = this.getOption('rootView');

            var type = request.get('type');
            var status = request.get('status');

            if(type === 'PICKUP'){
                if(status === 'OPEN') {
                    rootView.showAssignUserView(request);
                }
                else if (status === 'ASSIGNED'){
                    rootView.showAddNewRecordView(request);
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



