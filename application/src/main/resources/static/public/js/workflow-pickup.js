(function() {

    var PickupRequest = Backbone.Model.extend({
        urlRoot: '/api/pickuprequests'
    });

    var PickupRequests = Backbone.Collection.extend({
        model: PickupRequest,
        url: '/api/pickuprequests',

        parse: function(data) {
            return data.content;
        }
    });

    var ShowRequestView = Marionette.View.extend({
        template: '#show-request-template',
    });

    var AssignUserView = Marionette.View.extend({
        template: '#assign-user-template',
    });

    var EditRequestView = Marionette.View.extend({
        template: '#edit-request-template',

        initialize: function(){
            _.bindAll(this, "handleSaveSuccess", "handleSaveError");
        },

        events: {
            'click #save-button': 'handleSave'
        },

        onRender: function(){
            this.$('#storageType').select2({
                debug : false,
                theme : 'bootstrap',
                allowClear : true,
            });
            this.$('#storageType').empty().append('<option value="'+this.model.get('storageType').id+'">'+this.model.get('storageType').name+'</option>').val(this.model.get('storageType').id)

            this.$('#pickupDateTime').datetimepicker({
                step: 5,
                format: moment.javaToMomentDateFormat('yyyy-MM-dd HH:mm a'),
                datepicker : true,
                timepicker : true,
                formatDate : "YYYY/MM/DD",
                formatTime : "hh:mm A"
            });
            this.$('#pickupDateTime').val(moment(this.model.get('pickupDateTime')).format(moment.javaToMomentDateFormat('yyyy-MM-dd HH:mm a')));

            this.$('#numberFiles').val(this.model.get('numberFiles'));
            this.$('#documentType').val(this.model.get('documentType'));
            this.$('#notes').val(this.model.get('notes'));
        },

        handleSave: function(e){
            e.preventDefault();

            this.$('.form-group').removeClass('has-error has-feedback');
            this.$('.help-block').remove();

            this.model.save({
                    'storageType': this.$('#storageType').val(),
                    'pickupDateTime': this.$('#pickupDateTime').val(),
                    'numberFiles': this.$('#numberFiles').val(),
                    'documentType': this.$('#documentType').val(),
                    'notes': this.$('#notes').val()
                },
                {
                    success: this.handleSaveSuccess,
                    error: this.handleSaveError
                });

        },

        handleSaveSuccess: function(model, response)
        {
            //console.log('success', response);
            Backbone.history.navigate("/" + response.id, {trigger: true});
        },

        handleSaveError: function(model, response){
            //console.log('error', response.responseJSON.errors);

            _.each(response.responseJSON.errors, function(val, key) {
                $key = this.$('#'+key);
                $key.closest('.form-group').addClass('has-error has-feedback');
                $key.closest('div').append($('<span id="'+key+'-error" class="help-block">'+val+'</span>'));
            });
        }


    });

    var CreateRequestView = Marionette.View.extend({
        template: '#create-request-template',

        initialize: function(){
            _.bindAll(this, "handleSaveSuccess", "handleSaveError");
        },

        events: {
            'click #save-button': 'handleSave'
        },

        handleSave: function(e){
            e.preventDefault();

            this.$('.form-group').removeClass('has-error has-feedback');
            this.$('.help-block').remove();

            pickupRequest = new PickupRequest();
            pickupRequest.save({
                'storageType': this.$('#storageType').val(),
                'pickupDateTime': this.$('#pickupDateTime').val(),
                'numberFiles': this.$('#numberFiles').val(),
                'documentType': this.$('#documentType').val(),
                'notes': this.$('#notes').val()
            },
            {
                success: this.handleSaveSuccess,
                error: this.handleSaveError
            });

        },

        handleSaveSuccess: function(model, response)
        {
            //console.log('success', response);
            Backbone.history.navigate("/" + response.id, {trigger: true});
        },

        handleSaveError: function(model, response){
            //console.log('error', response.responseJSON.errors);

            _.each(response.responseJSON.errors, function(val, key) {
                $key = this.$('#'+key);
                $key.closest('.form-group').addClass('has-error has-feedback');
                $key.closest('div').append($('<span id="'+key+'-error" class="help-block">'+val+'</span>'));
            });
        }

    });

    var RootView = Marionette.View.extend({
        template: _.template('<div id="main"></div>'),

        regions: {
            main: '#main'
        },

        showCreateRequestView: function () {
            this.showChildView('main', new CreateRequestView());
        },

        showRequestView: function (request) {
            this.showChildView('main', new ShowRequestView({model: request}));
        },

        showEditRequestView: function (request) {
            this.showChildView('main', new EditRequestView({model: request}));
        },

        showAssignUserView: function (request) {
            this.showChildView('main', new AssignUserView({model: request}));
        }
    });

    var Controller = Marionette.Object.extend({
        createRequest() {
            var rootView = this.getOption('rootView');
            rootView.showCreateRequestView();
        },

        showRequest(requestId) {
            var rootView = this.getOption('rootView');

            pickupRequest = new PickupRequest({id: requestId});
            pickupRequest.fetch({
                success: function(model, response, options){
                    rootView.showRequestView(pickupRequest);
                },
                error: function(model, response, options){
                    console.log(response);
                }
            });
        },

        editRequest(requestId) {
            var rootView = this.getOption('rootView');

            pickupRequest = new PickupRequest({id: requestId});
            rootView.listenTo(pickupRequest, 'sync', rootView.showEditRequestView);
            pickupRequest.fetch();
        }
    });

    var Router = Marionette.AppRouter.extend({
        initialize(options) {
            this.controller = new Controller(options);
        },

        appRoutes: {
            '': 'createRequest',
            ':requestId': 'showRequest',
            ':requestId/edit': 'editRequest'
        }
    });

    var App = Marionette.Application.extend({
        region: '#app',

        onStart: function() {
            var rootView = new RootView();
            var router = new Router({rootView: rootView});
            this.showView(rootView);

            Backbone.history.start();
        }
    });

    var app = new App();
    app.start();

})();



