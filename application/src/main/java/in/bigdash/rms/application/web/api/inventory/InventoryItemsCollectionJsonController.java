package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.InventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.InventoryItemService;
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
 * = InventoryItemsCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = InventoryItem.class, pathPrefix = "/api", type = ControllerType.COLLECTION)
@RooJSON
@RestController
@RequestMapping(value = "/api/inventoryitems", name = "InventoryItemsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryItemsCollectionJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private InventoryItemService inventoryItemService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param inventoryItemService
     */
    @Autowired
    public InventoryItemsCollectionJsonController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return InventoryItemService
     */
    public InventoryItemService getInventoryItemService() {
        return inventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItemService
     */
    public void setInventoryItemService(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return ResponseEntity
     */
    @GetMapping(name = "list")
    public ResponseEntity<Page<InventoryItem>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<InventoryItem> inventoryItems = getInventoryItemService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(inventoryItems);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UriComponents
     */
    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(InventoryItemsCollectionJsonController.class).list(null, null)).build().encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItem
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody InventoryItem inventoryItem, BindingResult result) {
        if (inventoryItem.getId() != null || inventoryItem.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        InventoryItem newInventoryItem = getInventoryItemService().save(inventoryItem);
        UriComponents showURI = InventoryItemsItemJsonController.showURI(newInventoryItem);
        return ResponseEntity.created(showURI.toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItems
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<InventoryItem> inventoryItems, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getInventoryItemService().save(inventoryItems);
        return ResponseEntity.created(listURI().toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItems
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<InventoryItem> inventoryItems, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getInventoryItemService().save(inventoryItems);
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
        getInventoryItemService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
