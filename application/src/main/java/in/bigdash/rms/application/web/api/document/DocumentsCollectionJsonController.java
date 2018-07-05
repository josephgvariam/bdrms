package in.bigdash.rms.application.web.api.document;
import in.bigdash.rms.model.Document;

import in.bigdash.rms.service.api.DocumentService;
import io.springlets.data.domain.GlobalSearch;

import java.util.Arrays;
import java.util.Collection;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;


@RestController
@RequestMapping(value = "/api/documents", name = "DocumentsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DocumentService documentService;


    @Autowired
    public DocumentsCollectionJsonController(DocumentService documentService) {
        this.documentService = documentService;
    }


    public DocumentService getDocumentService() {
        return documentService;
    }


    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<Document>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<Document> documents = getDocumentService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(documents);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DocumentsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Document document, BindingResult result) {
        log.debug("create: {}", document);
        if (document.getId() != null || document.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", document, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Document newDocument = getDocumentService().save(document);
        log.debug("create saved: {}", document);
        UriComponents showURI = DocumentsItemJsonController.showURI(newDocument);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Document> documents, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(documents.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(documents.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getDocumentService().save(documents);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Document> documents, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(documents.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(documents.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getDocumentService().save(documents);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getDocumentService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
