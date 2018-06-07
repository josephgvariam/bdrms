package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;

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


@RestController
@RequestMapping(value = "/api/documentinventoryitems/{documentInventoryItem}", name = "DocumentInventoryItemsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentInventoryItemsItemJsonController {


    private DocumentInventoryItemService documentInventoryItemService;


    @Autowired
    public DocumentInventoryItemsItemJsonController(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }


    public DocumentInventoryItemService getDocumentInventoryItemService() {
        return documentInventoryItemService;
    }


    public void setDocumentInventoryItemService(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }


    @ModelAttribute
    public DocumentInventoryItem getDocumentInventoryItem(@PathVariable("documentInventoryItem") Long id) {
        DocumentInventoryItem documentInventoryItem = documentInventoryItemService.findOne(id);
        if (documentInventoryItem == null) {
            throw new NotFoundException(String.format("DocumentInventoryItem with identifier '%s' not found", id));
        }
        return documentInventoryItem;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute DocumentInventoryItem documentInventoryItem) {
        return ResponseEntity.ok(documentInventoryItem);
    }


    public static UriComponents showURI(DocumentInventoryItem documentInventoryItem) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DocumentInventoryItemsItemJsonController.class).show(documentInventoryItem)).buildAndExpand(documentInventoryItem.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute DocumentInventoryItem storedDocumentInventoryItem, @Valid @RequestBody DocumentInventoryItem documentInventoryItem, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        documentInventoryItem.setId(storedDocumentInventoryItem.getId());
        getDocumentInventoryItemService().save(documentInventoryItem);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute DocumentInventoryItem documentInventoryItem) {
        getDocumentInventoryItemService().delete(documentInventoryItem);
        return ResponseEntity.ok().build();
    }
}
