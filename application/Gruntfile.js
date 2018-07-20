module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        jshint: {
            files: [
                'src/main/resources/static/public/js/workflow-destruction.js',
                'src/main/resources/static/public/js/workflow-insertion.js',
                'src/main/resources/static/public/js/workflow-permout.js',
                'src/main/resources/static/public/js/workflow-pickup.js',
                'src/main/resources/static/public/js/workflow-refiling.js',
                'src/main/resources/static/public/js/workflow-retrieval.js',
                'src/main/resources/static/public/js/workflow-transfer.js',
                'src/main/resources/static/public/js/request.js',
            ],
            options: {
                globals: {
                    jQuery: true,
                    console: true,
                    module: true
                }
            }
        },
        uglifyPickup: {
            build: {
                src: 'src/main/resources/static/public/js/workflow-pickup.js',
                dest: 'src/main/resources/static/public/js/workflow-pickup.min.js'
            }
        },
        revPickup: {
            assets: {
                files: [{
                    src: [
                        'src/main/resources/static/public/js/workflow-pickup.min.js',
                    ]
                }]
            }
        },
        cssmin : {
            target : {
                src : ['src/main/resources/static/public/css/sweetalert.css'],
                dest : 'src/main/resources/static/public/css/sweetalert.min.css'
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-file-rev');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    //grunt.loadNpmTasks('grunt-filerev-replace');
    //grunt.loadNpmTasks('grunt-filerev-replace');
    // see https://martinsonesson.wordpress.com/tag/filerev/

    //grunt.registerTask('default', ['jshint', 'uglifyPickup', 'revPickup']);
    grunt.registerTask('default', ['jshint']);
    //grunt.registerTask('default', ['cssmin']);



};