(function() {

    var Request = Backbone.Model.extend({
        urlRoot: '/api/requests'
    });

    var AssignUserView = Marionette.View.extend({
        template: '#assign-user-template',
    });

    var RootView = Marionette.View.extend({
        template: _.template('<div id="main"></div>'),

        regions: {
            main: '#main'
        },

        showAssignUserView: function (request) {
            this.showChildView('main', new AssignUserView({model: request}));
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



