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
            type: null
        }
    });

    var InventoryItems = Backbone.Collection.extend({
        model: InventoryItem
    });

    var Box = Backbone.Model.extend({
        defaults: {
            barcode: '',
            location: ''
        }
    });

    var File = Backbone.Model.extend({
        defaults: {
            barcode: '',
            location: ''
        }
    });

    var Document = Backbone.Model.extend({
        defaults: {
            barcode: '',
            location: ''
        }
    });

    var Boxes = Backbone.Collection.extend({
        model: Box,
    });

    var Files = Backbone.Collection.extend({
        model: File,
    });

    var Documents = Backbone.Collection.extend({
        model: Document,
    });

//////////////////////// VIEWS


    var BoxRowView = Marionette.View.extend({
        tagName: 'tr',
        template: '#box-row-template',
        templateContext: function() {
            return {
                storageType: this.model.storageType
            };
        }
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

        },

        events: {
            'click #addBoxButton': 'addBox',
            'click #deleteBoxButton': 'deleteBox',
            'click div.ckbox>input': 'updateDeleteButtonEnabled',
            'click #editBoxButton': 'editBox',
            'click #showFilesButton': 'showFiles',
            'click #saveRecordsButton': 'saveRecords'
        },

        validateRecords: function(){
            //todo
            return true;
        },

        saveRecords: function(e){
            e.preventDefault();
            if(this.validateRecords()){
                //console.log(  JSON.stringify(this.collection.toJSON()) );
                //console.log(this.collection);

                var inventoryItems = new InventoryItems();

                this.collection.each(function(box){
                    if(box.get('inventoryItem')){
                        var b = box.clone();
                        b.unset('inventoryItem');

                        var i = box.get('inventoryItem').clone();
                        i.set('box', b);

                        inventoryItems.add(i);
                    }
                });

                console.log(  JSON.stringify(inventoryItems.toJSON()) );

                this.options.request.set('inventoryItems', inventoryItems);
                this.options.request.save();
            }

        },

        updateDeleteButtonEnabled: function() {
            if($('div.ckbox>input:checked').length){
                $('#deleteBoxButton').prop('disabled', false);
            }
            else{
                $('#deleteBoxButton').prop('disabled', true);
            }
        },

        showFiles: function(e){
            e.preventDefault();
            var barcode = String($(e.currentTarget).data('barcode'));
            var box = this.collection.findWhere({barcode: barcode})
            this.triggerMethod('show:files', box);
        },

        editBox: function(e){
            e.preventDefault();
            var barcode = String($(e.currentTarget).data('barcode'));
            var box = this.collection.findWhere({barcode: barcode});
            this.triggerMethod('show:box', box);
        },

        addBox: function(e) {
            var storageType = this.options.request.get('storageType').name;
            var box = new Box();
            box.storageType = storageType;

            if(storageType === 'BOX'){
                box.set('inventoryItem', new InventoryItem({type: 'BOX'}));
            }else{
                box.set('files', new Files());
            }

            this.triggerMethod('show:box', box);
        },

        deleteBox: function(e) {
            var checked = $('div.ckbox>input:checked');

            _.each(checked, function (b) {
                var barcode = String($(b).data('barcode'));

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

        templateContext: function() {
            return {
                storageType: this.model.storageType
            };
        },

        initialize: function(){
            this.isNewBox = this.model.get('barcode') === '';
        },

        events: {
            'click #ok-button': 'handleOkButton',
            'click #cancel-button': 'handleCancelButton',
        },

        validate: function(){
            this.clearValidationErrors();

            var valid = true;
            var barcode = this.$('#barcode').val();
            var location = this.$('#location').val();
            var ref1 = this.$('#ref1').val();

            if(!barcode){
                this.showValidationError('barcode', 'may not be empty');
                valid = false;
            }
            if(this.options.request.get('storageType').name === 'BOX' && !ref1){
                this.showValidationError('ref1', 'may not be empty');
                valid = false;
            }

            return valid;
        },

        handleOkButton: function(e){
            e.preventDefault();

            if(!this.validate()){
                return;
            }

            var barcode = this.$('#barcode').val();
            var location = this.$('#location').val();

            if(this.isNewBox && this.options.boxes.findWhere({barcode: barcode})){
                this.showValidationError('barcode', 'barcode already exists');
                return;
            }

            this.model.set({
                'barcode': barcode,
                'location': location,
            });

            if(this.options.request.get('storageType').name === 'BOX'){

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
            this.triggerMethod('show:boxes', this.model, this.isNewBox);
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
            if(this.isNewBox) {
                this.$('#barcode').focus();
            }else{
                this.$('#barcode').prop('readonly', true);
            }


        }

    });


    var FileRowView = Marionette.View.extend({
        tagName: 'tr',
        template: '#file-row-template',
        templateContext: function() {
            return {
                storageType: this.model.storageType
            };
        }
    });

    var EmptyFileRowView = Mn.View.extend({
        template: _.template('No Files. Start adding files.')
    });

    var FilesTableView = Marionette.CollectionView.extend({
        tagName: 'tbody',
        childView: FileRowView,
        emptyView: EmptyFileRowView,
    });

    var FilesView = Marionette.View.extend({
        template: '#files-template',

        regions: {
            body: {
                el: 'tbody',
                replaceElement: true
            }
        },

        initialize: function() {

        },

        events: {
            'click #addFileButton': 'addFile',
            'click #deleteFileButton': 'deleteFile',
            'click div.ckbox>input': 'updateDeleteButtonEnabled',
            'click #editFileButton': 'editFile',
            'click #showDocsButton': 'showDocs',
            'click #showBoxesButton': 'showBoxes'
        },

        showBoxes: function(e){
          this.triggerMethod('show:boxes');
        },

        updateDeleteButtonEnabled: function() {
            if($('div.ckbox>input:checked').length){
                $('#deleteFileButton').prop('disabled', false);
            }
            else{
                $('#deleteFileButton').prop('disabled', true);
            }
        },

        showDocs: function(e){
            e.preventDefault();
            var barcode = String($(e.currentTarget).data('barcode'));

            var file = this.collection.findWhere({barcode: barcode});

            this.triggerMethod('show:documents', file, this.options.box);
        },

        editFile: function(e){
            e.preventDefault();
            var barcode = String($(e.currentTarget).data('barcode'));
            var file = this.collection.findWhere({barcode: barcode})
            this.triggerMethod('show:file', file, this.options.box);
        },

        addFile: function(e) {
            var storageType = this.options.request.get('storageType').name;
            var file = new File();
            file.storageType = storageType;

            if(storageType === 'FILE'){
                file.set('inventoryItem', new InventoryItem({type: 'FILE'}));
            }else{
                file.set('documents', new Documents());
            }

            this.triggerMethod('show:file', file, this.options.box);
        },

        deleteFile: function(e) {
            var checked = $('div.ckbox>input:checked');

            _.each(checked, function (f) {
                var barcode = String($(f).data('barcode'));

                var file = this.collection.findWhere({barcode: barcode})

                this.collection.remove(file);
            }, this);

            this.updateDeleteButtonEnabled();
        },

        onRender() {
            this.$('#filesPanelTitle').text('BOX ' + this.options.box.get('barcode') + ' >> FILES');
            this.showChildView('body', new FilesTableView({collection: this.collection}));
        }
    });

    var FileView = Marionette.View.extend({
        template: '#file-template',

        templateContext: function() {
            return {
                storageType: this.model.storageType
            };
        },

        initialize: function(){
            this.isNewFile = this.model.get('barcode') === '';
        },

        events: {
            'click #ok-button': 'handleOkButton',
            'click #cancel-button': 'handleCancelButton',
        },

        validate: function(){
            this.clearValidationErrors();

            var valid = true;
            var barcode = this.$('#barcode').val();
            var location = this.$('#location').val();
            var ref1 = this.$('#ref1').val();

            if(!barcode){
                this.showValidationError('barcode', 'may not be empty');
                valid = false;
            }
            if(this.options.request.get('storageType').name === 'FILE' && !ref1){
                this.showValidationError('ref1', 'may not be empty');
                valid = false;
            }

            return valid;
        },

        handleOkButton: function(e){
            e.preventDefault();

            if(!this.validate()){
                return;
            }

            var barcode = this.$('#barcode').val();
            var location = this.$('#location').val();


            if(this.isNewFile && this.options.box.get('files').findWhere({barcode: barcode})){
                this.showValidationError('barcode', 'barcode already exists');
                return;
            }

            this.model.set({
                'barcode': barcode,
                'location': location,
            });

            if(this.options.request.get('storageType').name === 'FILE'){

                var inventoryItem = this.model.get('inventoryItem');
                inventoryItem.set('ref1', this.$('#ref1').val());
                inventoryItem.set('ref2', this.$('#ref2').val());
                inventoryItem.set('ref3', this.$('#ref3').val());
                inventoryItem.set('ref4', this.$('#ref4').val());
                inventoryItem.set('ref5', this.$('#ref5').val());

                this.model.set('inventoryItem', inventoryItem);

            }

            this.options.box.get('files').add(this.model, {merge: !this.isNewFile});

            //this.triggerMethod('show:files2', this.model, this.isNewFile, this.options.box);
            this.triggerMethod('show:files', this.options.box);
        },

        handleCancelButton: function(e){
            e.preventDefault();

            this.model = null;
            this.triggerMethod('show:files', this.options.box);
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
            var boxBarcode = this.options.box.get('barcode');
            var fileTitle = this.isNewFile ? 'NEW FILE' : 'FILE ' + this.model.get('barcode')
            this.$('#filePanelTitle').text('BOX ' + boxBarcode + ' >> ' + fileTitle);

            if(this.isNewFile) {
                this.$('#barcode').focus();
            }else{
                this.$('#barcode').prop('readonly', true);
            }

        }

    });


    var DocumentRowView = Marionette.View.extend({
        tagName: 'tr',
        template: '#document-row-template',
        templateContext: function() {
            return {
                storageType: this.model.storageType
            };
        }
    });

    var EmptyDocumentRowView = Mn.View.extend({
        template: _.template('No Documents. Start adding documents.')
    });

    var DocumentsTableView = Marionette.CollectionView.extend({
        tagName: 'tbody',
        childView: DocumentRowView,
        emptyView: EmptyDocumentRowView,
    });

    var DocumentsView = Marionette.View.extend({
        template: '#documents-template',

        regions: {
            body: {
                el: 'tbody',
                replaceElement: true
            }
        },

        initialize: function() {

        },

        events: {
            'click #addDocumentButton': 'addDocument',
            'click #deleteDocumentButton': 'deleteDocument',
            'click div.ckbox>input': 'updateDeleteButtonEnabled',
            'click #editDocumentButton': 'editDocument',
            'click #showFilesButton': 'showFiles'
        },

        showFiles: function(e){
            this.triggerMethod('show:files', this.options.box);
        },

        updateDeleteButtonEnabled: function() {
            if($('div.ckbox>input:checked').length){
                $('#deleteDocumentButton').prop('disabled', false);
            }
            else{
                $('#deleteDocumentButton').prop('disabled', true);
            }
        },

        editDocument: function(e){
            e.preventDefault();
            var barcode = String($(e.currentTarget).data('barcode'));
            var document = this.collection.findWhere({barcode: barcode})
            this.triggerMethod('show:document', document, this.options.file, this.options.box);
        },

        addDocument: function(e) {
            var storageType = this.options.request.get('storageType').name;
            var document = new Document();
            document.storageType = storageType;

            if(storageType === 'DOCUMENT'){
                document.set('inventoryItem', new InventoryItem({type: 'DOCUMENT'}));
            }

            this.triggerMethod('show:document', document, this.options.file, this.options.box);
        },

        deleteDocument: function(e) {
            var checked = $('div.ckbox>input:checked');

            _.each(checked, function (d) {
                var barcode = String($(d).data('barcode'));

                var document = this.collection.findWhere({barcode: barcode})

                this.collection.remove(document);
            }, this);

            this.updateDeleteButtonEnabled();
        },

        onRender() {
            var boxBarcode = this.options.box.get('barcode');
            var fileBarcode = this.options.file.get('barcode');
            this.$('#documentsPanelTitle').text('BOX ' + boxBarcode + ' >> FILE ' + fileBarcode + ' >> DOCUMENTS');
            this.showChildView('body', new DocumentsTableView({collection: this.collection}));
        }
    });

    var DocumentView = Marionette.View.extend({
        template: '#document-template',

        templateContext: function() {
            return {
                storageType: this.model.storageType
            };
        },

        initialize: function(){
            this.isNewDocument = this.model.get('barcode') === '';
        },

        events: {
            'click #ok-button': 'handleOkButton',
            'click #cancel-button': 'handleCancelButton',
        },

        validate: function(){
            this.clearValidationErrors();

            var valid = true;
            var barcode = this.$('#barcode').val();
            var location = this.$('#location').val();
            var ref1 = this.$('#ref1').val();

            if(!barcode){
                this.showValidationError('barcode', 'may not be empty');
                valid = false;
            }
            if(this.options.request.get('storageType').name === 'DOCUMENT' && !ref1){
                this.showValidationError('ref1', 'may not be empty');
                valid = false;
            }

            return valid;
        },

        handleOkButton: function(e){
            e.preventDefault();

            if(!this.validate()){
                return;
            }

            var barcode = this.$('#barcode').val();
            var location = this.$('#location').val();

            if(this.isNewDocument && this.options.file.get('documents').findWhere({barcode: barcode})){
                this.showValidationError('barcode', 'barcode already exists');
                return;
            }

            this.model.set({
                'barcode': barcode,
                'location': location,
            });

            if(this.options.request.get('storageType').name === 'DOCUMENT'){

                var inventoryItem = this.model.get('inventoryItem');
                inventoryItem.set('ref1', this.$('#ref1').val());
                inventoryItem.set('ref2', this.$('#ref2').val());
                inventoryItem.set('ref3', this.$('#ref3').val());
                inventoryItem.set('ref4', this.$('#ref4').val());
                inventoryItem.set('ref5', this.$('#ref5').val());

                this.model.set('inventoryItem', inventoryItem);

            }

            this.options.file.get('documents').add(this.model, {merge: !this.isNewDocument});

            this.triggerMethod('show:documents', this.options.file, this.options.box);
        },

        handleCancelButton: function(e){
            e.preventDefault();

            this.model = null;
            this.triggerMethod('show:documents', this.options.file, this.options.box);
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
            var boxBarcode = this.options.box.get('barcode');
            var fileBarcode = this.options.file.get('barcode');
            var documentTitle = this.isNewDocument ? 'NEW DOCUMENT' : 'DOCUMENT ' + this.model.get('barcode')
            this.$('#documentPanelTitle').text('BOX ' + boxBarcode + ' >> FILE ' + fileBarcode + ' >> DOCUMENT ' + documentTitle);

            if(this.isNewDocument) {
                this.$('#barcode').focus();
            }else{
                this.$('#barcode').prop('readonly', true);
            }

        }

    });



    var AddNewRecordsView = Marionette.View.extend({
        template: '#add-new-record-template',

        regions: {
            body: {el: '#add-new-record', replaceElement: true}
        },

        getTestData: function(storageType){

            var barcodeCount = 1;
            var depth = 3;

            var boxes = new Boxes();

            for (var b = 1; b <= depth; b++) {
                var box = new Box({barcode: 'BO-' + barcodeCount++});
                box.storageType = storageType; //need in template, but not in attributes and its json

                if(storageType === 'BOX'){
                    box.set('inventoryItem', new InventoryItem({type: 'BOX'}));
                }else{
                    var files = new Files();

                    for(var f = 1; f <= depth; f++ ){

                        var file = new File({barcode: 'FL-' + barcodeCount++});
                        file.storageType = storageType;

                        if(storageType === 'FILE'){
                            file.set('inventoryItem', new InventoryItem({type: 'FILE'}));
                        }else{
                            var docs = new Documents();

                            for(var d = 1; d <= depth; d++ ){
                                var doc = new Document({barcode: 'DO' + barcodeCount++});
                                doc.storageType = storageType;
                                doc.set('inventoryItem', new InventoryItem({type: 'DOCUMENT'}));
                                docs.add(doc);
                            }

                            file.set('documents', docs);
                        }

                        files.add(file);
                    }

                    box.set('files', files);
                }

                boxes.add(box);
            }

            return boxes;
        },

        initialize: function(){
            this.boxes = new Boxes();
            //this.boxes = this.getTestData(this.model.get('storageType').name);

        },

        onRender: function() {
            this.showBoxesView();
        },

        onChildviewShowBox: function(box) {
            this.showChildView('body', new BoxView({model: box, boxes: this.boxes, request: this.model}));
        },

        onChildviewShowBoxes: function(box, isNewBox) {
            if(box){
                this.boxes.add(box, {merge: !isNewBox});
            }

            this.showBoxesView();
        },

        showBoxesView: function(){
            this.showChildView('body', new BoxesView({collection: this.boxes, request: this.model}));
        },

//////

        onChildviewShowFile: function(file, box) {
            this.showChildView('body', new FileView({model: file, box: box, request: this.model}));
        },

        onChildviewShowFiles: function(box){
            this.showChildView('body', new FilesView({collection: box.get('files'), box: box, request: this.model}));
        },
//////

        onChildviewShowDocument: function(document, file, box) {
            this.showChildView('body', new DocumentView({model: document, file: file, box: box, request: this.model}));
        },

        onChildviewShowDocuments: function(file, box){
            this.showChildView('body', new DocumentsView({collection: file.get('documents'), file: file, box: box, request: this.model}));
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
            this.model.save({'status': this.options.nextStatus}, {success: this.handleSaveSuccess, error: this.handleSaveError});
        },

        cancel: function(e){
            e.preventDefault();
            window.location.href='/requests/';
        },

        handleSaveSuccess: function(model, response)
        {
            Backbone.history.navigate(response.id + '/workflow/' + this.options.navText, {trigger: false});
            this.options.nextViewFunction.apply(this.options.rootView, [this.model]);
        },

        handleSaveError: function(model, response)
        {
            console.log('error saving request', response);
        },

        onAttach: function(){

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

        showBeforeProcessRequestView: function(request, title, msg, nextStatus, nextViewFunction, navText){
            this.showChildView('main', new BeforeProcessRequestView({model: request, rootView: this, title: title, msg: msg, nextStatus: nextStatus, nextViewFunction: nextViewFunction, navText: navText}));
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
        start: function(requestId, splat) {
            var request = new Request({id: requestId});
            this.listenToOnce(request, 'sync', this.workflow);
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
                    rootView.showBeforeProcessRequestView(request, 'Add Records','Proceed with adding records to this request?', 'INPROGRESS', rootView.showAddNewRecordView, 'addRecords');
                }
                else if (status === 'INPROGRESS'){
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
            ':requestId/workflow/*splat': 'start',
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



