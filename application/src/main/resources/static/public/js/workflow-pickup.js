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

            this.$('.form-group').removeClass('has-error has-feedback');
            this.$('.help-block').remove();

            if(!this.$('#userAssigned').val()){
                this.showValidationErrors('userAssigned', 'may not be null');
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
            //console.log('error', response.responseJSON.errors);

            _.each(response.responseJSON.errors, function(val, key) {
                this.showValidationErrors(key, val);
            });
        },

        showValidationErrors: function (key, val) {
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

            if(status === 'OPEN') {
                rootView.showAssignUserView(request);
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



