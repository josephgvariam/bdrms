package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.application.security.JpaUserDetails;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;

import in.bigdash.rms.service.api.DocumentInventoryItemService;
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
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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
@RequestMapping(value = "/api/documentinventoryitems", name = "DocumentInventoryItemsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentInventoryItemsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DocumentInventoryItemService documentInventoryItemService;


    @Autowired
    Validator validator;


    @Autowired
    public DocumentInventoryItemsCollectionJsonController(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }


    public DocumentInventoryItemService getDocumentInventoryItemService() {
        return documentInventoryItemService;
    }


    public void setDocumentInventoryItemService(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<DocumentInventoryItem>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<DocumentInventoryItem> documentInventoryItems = getDocumentInventoryItemService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(documentInventoryItems);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DocumentInventoryItemsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody DocumentInventoryItem documentInventoryItem, BindingResult result, Authentication authentication) {
        log.debug("create: {}", documentInventoryItem);

        documentInventoryItem.setUserCreated(((JpaUserDetails)authentication.getPrincipal()).getUser());

        validator.validate(documentInventoryItem, result);

        if (documentInventoryItem.getId() != null || documentInventoryItem.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", documentInventoryItem, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        DocumentInventoryItem newDocumentInventoryItem = getDocumentInventoryItemService().save(documentInventoryItem);
        log.debug("create saved: {}", documentInventoryItem);
        UriComponents showURI = DocumentInventoryItemsItemJsonController.showURI(newDocumentInventoryItem);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<DocumentInventoryItem> documentInventoryItems, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(documentInventoryItems.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(documentInventoryItems.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getDocumentInventoryItemService().save(documentInventoryItems);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<DocumentInventoryItem> documentInventoryItems, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(documentInventoryItems.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(documentInventoryItems.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getDocumentInventoryItemService().save(documentInventoryItems);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getDocumentInventoryItemService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
