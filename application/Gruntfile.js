module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        jshint: {
            files: ['src/main/resources/static/public/js/workflow.js'],
            options: {
                globals: {
                    jQuery: true,
                    console: true,
                    module: true
                }
            }
        },
        uglify: {
            build: {
                src: 'src/main/resources/static/public/js/workflow.js',
                dest: 'src/main/resources/static/public/js/workflow.min.js'
            }
        },
        rev: {
            assets: {
                files: [{
                    src: [
                        'src/main/resources/static/public/js/workflow.min.js',
                    ]
                }]
            }
        },
    });

    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-file-rev');
    //grunt.loadNpmTasks('grunt-filerev-replace');
    //grunt.loadNpmTasks('grunt-filerev-replace');
    // see https://martinsonesson.wordpress.com/tag/filerev/

    grunt.registerTask('default', ['jshint', 'uglify', 'rev']);



};