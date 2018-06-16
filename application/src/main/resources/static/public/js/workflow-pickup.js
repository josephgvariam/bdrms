(function() {

    var PickupRequest = Backbone.Model.extend({
        url: '/api/pickuprequests'
    });

    var PickupRequests = Backbone.Collection.extend({
        model: PickupRequest,
        url: '/api/pickuprequests',

        parse: function(data) {
            return data.content;
        }
    });

    var CreateFormView = Marionette.View.extend({
        template: '#create-form-template',

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

        showCreateForm : function () {
            this.showChildView('main', new CreateFormView());
        }
    });

    var Controller = Marionette.Object.extend({
        default() {
            var rootView = this.getOption('rootView');
            rootView.showCreateForm();
        },

        doit(requestId) {
            alert(requestId);
            console.log(requestId);
        }
    });

    var Router = Marionette.AppRouter.extend({
        initialize(options) {
            this.controller = new Controller(options);
        },

        appRoutes: {
            '': 'default',
            ':requestId': 'doit'
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



