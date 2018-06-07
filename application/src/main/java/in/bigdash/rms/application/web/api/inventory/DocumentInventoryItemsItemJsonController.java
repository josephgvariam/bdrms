package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.DocumentInventoryItemService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

/**
 * = DocumentInventoryItemsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = DocumentInventoryItem.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/documentinventoryitems/{documentInventoryItem}", name = "DocumentInventoryItemsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentInventoryItemsItemJsonController {

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
    public DocumentInventoryItemsItemJsonController(DocumentInventoryItemService documentInventoryItemService) {
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
     * @param id
     * @return DocumentInventoryItem
     */
    @ModelAttribute
    public DocumentInventoryItem getDocumentInventoryItem(@PathVariable("documentInventoryItem") Long id) {
        DocumentInventoryItem documentInventoryItem = documentInventoryItemService.findOne(id);
        if (documentInventoryItem == null) {
            throw new NotFoundException(String.format("DocumentInventoryItem with identifier '%s' not found", id));
        }
        return documentInventoryItem;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute DocumentInventoryItem documentInventoryItem) {
        return ResponseEntity.ok(documentInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     * @return UriComponents
     */
    public static UriComponents showURI(DocumentInventoryItem documentInventoryItem) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DocumentInventoryItemsItemJsonController.class).show(documentInventoryItem)).buildAndExpand(documentInventoryItem.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedDocumentInventoryItem
     * @param documentInventoryItem
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute DocumentInventoryItem storedDocumentInventoryItem, @Valid @RequestBody DocumentInventoryItem documentInventoryItem, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        documentInventoryItem.setId(storedDocumentInventoryItem.getId());
        getDocumentInventoryItemService().save(documentInventoryItem);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute DocumentInventoryItem documentInventoryItem) {
        getDocumentInventoryItemService().delete(documentInventoryItem);
        return ResponseEntity.ok().build();
    }
}
