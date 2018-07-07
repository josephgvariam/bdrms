(function() {

    var RootView = Marionette.View.extend({
        template: _.template('<div id="main"></div>'),

        regions: {
            main: '#main'
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