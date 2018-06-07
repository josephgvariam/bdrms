package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.InventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.InventoryItemService;
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
 * = InventoryItemsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = InventoryItem.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/inventoryitems/{inventoryItem}", name = "InventoryItemsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryItemsItemJsonController {

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
    public InventoryItemsItemJsonController(InventoryItemService inventoryItemService) {
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
     * @param id
     * @return InventoryItem
     */
    @ModelAttribute
    public InventoryItem getInventoryItem(@PathVariable("inventoryItem") Long id) {
        InventoryItem inventoryItem = inventoryItemService.findOne(id);
        if (inventoryItem == null) {
            throw new NotFoundException(String.format("InventoryItem with identifier '%s' not found", id));
        }
        return inventoryItem;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItem
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute InventoryItem inventoryItem) {
        return ResponseEntity.ok(inventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItem
     * @return UriComponents
     */
    public static UriComponents showURI(InventoryItem inventoryItem) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(InventoryItemsItemJsonController.class).show(inventoryItem)).buildAndExpand(inventoryItem.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedInventoryItem
     * @param inventoryItem
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute InventoryItem storedInventoryItem, @Valid @RequestBody InventoryItem inventoryItem, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        inventoryItem.setId(storedInventoryItem.getId());
        getInventoryItemService().save(inventoryItem);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItem
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute InventoryItem inventoryItem) {
        getInventoryItemService().delete(inventoryItem);
        return ResponseEntity.ok().build();
    }
}
