package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.DocumentInventoryItemService;
import io.springlets.data.domain.GlobalSearch;
import java.util.Collection;
import javax.validation.Valid;
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

/**
 * = DocumentInventoryItemsCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = DocumentInventoryItem.class, pathPrefix = "/api", type = ControllerType.COLLECTION)
@RooJSON
@RestController
@RequestMapping(value = "/api/documentinventoryitems", name = "DocumentInventoryItemsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentInventoryItemsCollectionJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DocumentInventoryItemService documentInventoryItemService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param documentInventoryItemService
     */
    @Autowired
    public DocumentInventoryItemsCollectionJsonController(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DocumentInventoryItemService
     */
    public DocumentInventoryItemService getDocumentInventoryItemService() {
        return documentInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItemService
     */
    public void setDocumentInventoryItemService(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return ResponseEntity
     */
    @GetMapping(name = "list")
    public ResponseEntity<Page<DocumentInventoryItem>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<DocumentInventoryItem> documentInventoryItems = getDocumentInventoryItemService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(documentInventoryItems);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UriComponents
     */
    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DocumentInventoryItemsCollectionJsonController.class).list(null, null)).build().encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody DocumentInventoryItem documentInventoryItem, BindingResult result) {
        if (documentInventoryItem.getId() != null || documentInventoryItem.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        DocumentInventoryItem newDocumentInventoryItem = getDocumentInventoryItemService().save(documentInventoryItem);
        UriComponents showURI = DocumentInventoryItemsItemJsonController.showURI(newDocumentInventoryItem);
        return ResponseEntity.created(showURI.toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItems
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<DocumentInventoryItem> documentInventoryItems, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getDocumentInventoryItemService().save(documentInventoryItems);
        return ResponseEntity.created(listURI().toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItems
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<DocumentInventoryItem> documentInventoryItems, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getDocumentInventoryItemService().save(documentInventoryItems);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return ResponseEntity
     */
    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        getDocumentInventoryItemService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
