(function() {


    var util = (function () {
        return {
            showInfoModal: function(title, message, onHideFunction){
                var modal = $('#infoModal');
                modal.find('.modal-title').text(title);
                modal.find('.modal-body').html('<p>' + message + '</p>');

                if(onHideFunction) {
                    modal.on('hidden.bs.modal', onHideFunction);
                }

                modal.modal();
            },

            getBoxes: function(request, isFromStorage){
                var boxes;

                if(isFromStorage){
                    boxes = storage.fetchRecords(request)
                }else{
                    boxes = new Boxes();
                }

                if(boxes.size() === 0) {
                    var inventoryItems = request.get('inventoryItems');
                    var storageType = request.get('storageType').name;

                    _.each(inventoryItems, function (item) {
                        var i = new InventoryItem({
                            id: item.id,
                            ref1: item.ref1,
                            ref2: item.ref2,
                            ref3: item.ref3,
                            ref4: item.ref4,
                            ref5: item.ref5,
                            type: item.type,
                            status: item.status
                        });

                        if (item.type === 'BOX') {
                            var b = new Box({
                                id: item.box.id,
                                barcode: item.box.barcode,
                                location: item.box.location,
                                inventoryItem: i
                            });
                            b.storageType = storageType;

                            boxes.add(b);
                        }
                        else if (item.type === 'FILE') {
                            var f = new File({
                                id: item.file.id,
                                barcode: item.file.barcode,
                                location: item.file.location,
                                inventoryItem: i
                            });
                            f.storageType = storageType;

                            var b = boxes.findWhere({barcode: item.file.box.barcode});
                            if (!b) {
                                b = new Box({
                                    id: item.file.box.id,
                                    barcode: item.file.box.barcode,
                                    location: item.file.box.location,
                                    files: new Files()
                                });
                                b.storageType = storageType;
                                boxes.add(b);
                            }
                            b.get('files').add(f);
                        }
                        else if (item.type === 'DOCUMENT') {
                            var d = new Document({
                                id: item.document.id,
                                barcode: item.document.barcode,
                                location: item.document.location,
                                inventoryItem: i
                            });
                            d.storageType = storageType;

                            var b = boxes.findWhere({barcode: item.document.file.box.barcode});
                            if (!b) {
                                b = new Box({
                                    id: item.document.file.box.id,
                                    barcode: item.document.file.box.barcode,
                                    location: item.document.file.box.location,
                                    files: new Files()
                                });
                                b.storageType = storageType;
                                boxes.add(b);
                            }

                            var f = b.get('files').findWhere({barcode: item.document.file.barcode});
                            if (!f) {
                                f = new File({
                                    id: item.document.file.id,
                                    barcode: item.document.file.barcode,
                                    location: item.document.file.location,
                                    documents: new Documents()
                                });
                                f.storageType = storageType;
                                b.get('files').add(f);
                            }

                            f.get('documents').add(d);

                        }
                    });
                }
                return boxes;
            },

        };
    })();

    var storage = (function () {

        BdrmsStorageItem = Backbone.Model.extend();

        BdrmsStorage = Backbone.Collection.extend({
            model: BdrmsStorageItem,
            localStorage: new Backbone.LocalStorage("in.bigdash.rms.app.storage"),
        });

        bdrmsStorage = new BdrmsStorage();

        return {

            storeBox: function( box, request ) {
                var record = new Box({
                    id: box.get('barcode'),
                    location: box.get('location'),
                    requestId: request.get('id'),
                    storageType: request.get('storageType').name,
                    type: 'BOX'
                });

                if(request.get('storageType').name === 'BOX'){
                    record.set({
                        ref1: box.get('inventoryItem').get('ref1'),
                        ref2: box.get('inventoryItem').get('ref2'),
                        ref3: box.get('inventoryItem').get('ref3'),
                        ref4: box.get('inventoryItem').get('ref4'),
                        ref5: box.get('inventoryItem').get('ref5'),
                        type: box.get('inventoryItem').get('type'),
                        status: box.get('inventoryItem').get('status')
                    });
                }

                bdrmsStorage.add(record);
                record.save();
            },

            storeFile: function( file, box, request ) {
                var record = new File({
                    id: file.get('barcode'),
                    location: file.get('location'),
                    parentId: box.get('barcode'),
                    requestId: request.get('id'),
                    storageType: request.get('storageType').name,
                    type: 'FILE'
                });

                if(request.get('storageType').name === 'FILE'){
                    record.set({
                        ref1: file.get('inventoryItem').get('ref1'),
                        ref2: file.get('inventoryItem').get('ref2'),
                        ref3: file.get('inventoryItem').get('ref3'),
                        ref4: file.get('inventoryItem').get('ref4'),
                        ref5: file.get('inventoryItem').get('ref5'),
                        type: file.get('inventoryItem').get('type'),
                        status: file.get('inventoryItem').get('status')
                    });
                }

                bdrmsStorage.add(record);
                record.save();
            },

            storeDocument: function( doc, file, request ) {
                var record = new Document({
                    id: doc.get('barcode'),
                    location: doc.get('location'),
                    parentId: file.get('barcode'),
                    requestId: request.get('id'),
                    storageType: request.get('storageType').name,
                    type: 'DOCUMENT'
                });

                if(request.get('storageType').name === 'DOCUMENT'){
                    record.set({
                        ref1: doc.get('inventoryItem').get('ref1'),
                        ref2: doc.get('inventoryItem').get('ref2'),
                        ref3: doc.get('inventoryItem').get('ref3'),
                        ref4: doc.get('inventoryItem').get('ref4'),
                        ref5: doc.get('inventoryItem').get('ref5'),
                        type: doc.get('inventoryItem').get('type'),
                        status: doc.get('inventoryItem').get('status')
                    });
                }

                bdrmsStorage.add(record);
                record.save();
            },

            remove: function( record ) {
                var r = bdrmsStorage.get( record.get('barcode') );
                r.destroy();
            },

            fetchRecords: function( request ) {
                bdrmsStorage.fetch();

                var records = bdrmsStorage.where( {requestId: request.get('id')} );

                var boxes = new Boxes();
                var files = new Files();
                var docs = new Documents();

                _.each(records, function( record ){
                    var type = record.get('type');

                    if(type === 'BOX'){
                        var b = new Box({
                            barcode: record.get('id'),
                            location: record.get('location')
                        });
                        b.storageType = record.get('storageType');

                        if(b.storageType === 'BOX'){
                            var i = new InventoryItem({
                                ref1: record.get('ref1'),
                                ref2: record.get('ref2'),
                                ref3: record.get('ref3'),
                                ref4: record.get('ref4'),
                                ref5: record.get('ref5'),
                                type: record.get('type'),
                                status: record.get('status')
                            });

                            b.set('inventoryItem', i);
                        }
                        else{
                            b.set('files', new Files());
                        }

                        boxes.add(b);
                    }
                    else if(type === 'FILE'){
                        var f = new File({
                            barcode: record.get('id'),
                            location: record.get('location')
                        });
                        f.storageType = record.get('storageType');
                        f.parentBarcode = record.get('parentId');

                        if(f.storageType === 'FILE'){
                            var i = new InventoryItem({
                                ref1: record.get('ref1'),
                                ref2: record.get('ref2'),
                                ref3: record.get('ref3'),
                                ref4: record.get('ref4'),
                                ref5: record.get('ref5'),
                                type: record.get('type'),
                                status: record.get('status')
                            });

                            f.set('inventoryItem', i);
                        }
                        else{
                            f.set('documents', new Documents());
                        }

                        files.add(f);
                    }
                    else if(type === 'DOCUMENT'){
                        var d = new Document({
                            barcode: record.get('id'),
                            location: record.get('location')
                        });
                        d.storageType = record.get('storageType');
                        d.parentBarcode = record.get('parentId');

                        var i = new InventoryItem({
                            ref1: record.get('ref1'),
                            ref2: record.get('ref2'),
                            ref3: record.get('ref3'),
                            ref4: record.get('ref4'),
                            ref5: record.get('ref5'),
                            type: record.get('type'),
                            status: record.get('status')
                        });

                        d.set('inventoryItem', i);

                        docs.add(d);
                    }
                });

                docs.each(function(doc){
                    var file = files.findWhere({barcode: doc.parentBarcode});
                    file.get('documents').add(doc);
                });

                files.each(function(file){
                    var box = boxes.findWhere({barcode: file.parentBarcode});
                    box.get('files').add(file);
                });

                return boxes;
            },

            clearRecords: function(request){
                var records = bdrmsStorage.where( {requestId: request.get('id')} );
                _.each(records, function( record ){
                    record.destroy();
                });
            }
        };
    })();

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

        initialize: function(){
            _.bindAll(this, "handleRecordsSaveSuccess", "handleRecordsSaveError");
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

            $('#workflowAlert').html('');

            var storageType = this.options.request.get('storageType').name;
            var errors = [];

            if(this.collection.size() === 0){
                errors.push('No boxes to save');
            }

            if(storageType === 'BOX'){

            }
            else if(storageType === 'FILE'){
                this.collection.each(function (box) {
                    if(box.get('files').size() === 0) {
                        errors.push('Box ' + box.get('barcode') + ' does not have any files');
                    }
                });
            }
            else if(storageType === 'DOCUMENT'){
                this.collection.each(function (box) {
                    if(box.get('files').size() === 0) {
                        errors.push('Box ' + box.get('barcode') + ' does not have any files');
                    }
                    else{
                        box.get('files').each(function (file) {
                            if(file.get('documents').size() === 0) {
                                errors.push('Box ' + box.get('barcode') + ', File ' + file.get('barcode') + ' does not have any documents');
                            }
                        });
                    }
                });
            }

            //console.log(errors);

            if(errors.length) {
                this.showAlert(errors);
            }

            return errors.length === 0;

        },

        showAlert: function(errors){
            var alertText = '';

            if(errors.length === 1){
                alertText = '<div id="workflowAlert" class="alert alert-danger alert-dismissible col-md-10 col-md-offset-1"><strong>Error saving records!</strong> '+ errors[0] +' <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>';
            }
            else{
                alertText = '<div id="workflowAlert" class="alert alert-danger alert-dismissible col-md-10 col-md-offset-1"><strong>Error saving records!</strong> Please check the following: <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

                _.each(errors, function (e) {
                    alertText = alertText + '<li>' + e + '</li>';
                });

                alertText = alertText + '</div>';
            }

            $('#alert').html(alertText);
        },

        saveRecords: function(e){
            e.preventDefault();
            if(this.validateRecords()){
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
                    else{
                        box.get('files').each(function (file) {
                            if(file.get('inventoryItem')){
                                var f = file.clone();
                                var b = box.clone();
                                f.unset('inventoryItem');
                                b.unset('files');
                                f.set('box', b);

                                var i = file.get('inventoryItem').clone();
                                i.set('file', f)

                                inventoryItems.add(i);
                            }
                            else{
                                file.get('documents').each(function (doc) {
                                    var d = doc.clone();
                                    var f = file.clone();
                                    var b = box.clone();
                                    d.unset('inventoryItem');
                                    f.unset('documents')
                                    b.unset('files');
                                    f.set('box', b);
                                    d.set('file', f);

                                    var i = doc.get('inventoryItem').clone();
                                    i.set('document', d)

                                    inventoryItems.add(i);
                                });
                            }
                        });
                    }
                });

                this.options.request.save({
                    inventoryItems: inventoryItems,
                    status: 'PACKED'
                }, {
                    wait: true,
                    success: this.handleRecordsSaveSuccess,
                    error: this.handleRecordsSaveError
                });
            }

        },

        handleRecordsSaveSuccess: function(model, response, options){
            storage.clearRecords(this.options.request);
            util.showInfoModal('Info', 'Records saved successfully.', function(){window.location.href='/requests/' + response.id + '/workflow'});
        },

        handleRecordsSaveError: function(model, response, options){
            if(response.responseJSON.message) {
                this.showAlert([response.responseJSON.message]);
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

            var skipped = [];

            _.each(checked, function (b) {
                var barcode = String($(b).data('barcode'));

                var box = this.collection.findWhere({barcode: barcode});

                if(box.get('files') && box.get('files').size()){
                    skipped.push(box.get('barcode'));
                    return;
                }

                this.collection.remove(box);
                storage.remove(box);
            }, this);

            if(skipped.length > 0) {
                util.showInfoModal('Delete', 'Cannot delete non empty records! Skipped boxes: ' + skipped.join(", "));
            }

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

            var skipped = [];

            _.each(checked, function (f) {
                var barcode = String($(f).data('barcode'));

                var file = this.collection.findWhere({barcode: barcode})

                if(file.get('documents') && file.get('documents').size()){
                    skipped.push(file.get('barcode'));
                    return;
                }

                this.collection.remove(file);
                storage.remove(file);
            }, this);

            if(skipped.length > 0) {
                util.showInfoModal('Delete', 'Cannot delete non empty records! Skipped files: ' + skipped.join(", "));
            }

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
            storage.storeFile(this.model, this.options.box, this.options.request);

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
                storage.remove(document);
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
            storage.storeDocument(this.model, this.options.file, this.options.request);

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
            this.$('#documentPanelTitle').text('BOX ' + boxBarcode + ' >> FILE ' + fileBarcode + ' >>  ' + documentTitle);

            if(this.isNewDocument) {
                this.$('#barcode').focus();
            }else{
                this.$('#barcode').prop('readonly', true);
            }

        }

    });



    var AddRecordsView = Marionette.View.extend({
        template: '#add-records-template',

        regions: {
            body: {el: '#add-records', replaceElement: true}
        },



        initialize: function(){
            //this.boxes = new Boxes();

            //this.boxes = this.getTestData(this.model.get('storageType').name);
            this.boxes = util.getBoxes(this.model, true);


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
                storage.storeBox(box, this.model);
            }

            this.showBoxesView();
        },

        showBoxesView: function(){
            this.showChildView('body', new BoxesView({collection: this.boxes, request: this.model, rootView: this.options.rootView}));
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
                    wait: true,
                    success: this.handleSaveSuccess,
                    error: this.handleSaveError
                });

        },

        handleSaveSuccess: function(model, response)
        {
            util.showInfoModal('Info', 'User has been assigned successfully.', function(){window.location.href='/requests/' + response.id});
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
            this.model.save({'status': this.options.nextStatus}, {wait: true, success: this.handleSaveSuccess, error: this.handleSaveError});
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

    var ViewRecordsView = Marionette.View.extend({
        template: '#view-records-template',

        regions: {
            body: {el: '#view-records', replaceElement: true}
        },

        initialize: function(){
            //_.bindAll(this, "handleSaveSuccess", "handleSaveError");
        },

        events: {
            //'click #save-button': 'handleSave'
        },
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

        showAddRecordsView: function (request) {
            this.showChildView('main', new AddRecordsView({model: request, rootView: this}));
        },

        showViewRecordsView: function (request) {
            this.showChildView('main', new ViewRecordsView({model: request, rootView: this}));
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

            if(type === 'PICKUP'){
                if(status === 'OPEN') {
                    rootView.showAssignUserView(request);
                }
                else if (status === 'ASSIGNED'){
                    rootView.showBeforeProcessRequestView(request, 'Add Records','Proceed with adding records to this request?', 'INPROGRESS', rootView.showAddRecordsView, 'addRecords');
                }
                else if (status === 'INPROGRESS'){
                    rootView.showAddRecordsView(request);
                }
                else if (status === 'PACKED' || status === 'TRANSIT'){
                    rootView.showViewRecordsView(request);
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



