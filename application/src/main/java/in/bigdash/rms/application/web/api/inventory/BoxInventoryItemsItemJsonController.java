package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.BoxInventoryItemService;
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
 * = BoxInventoryItemsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = BoxInventoryItem.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/boxinventoryitems/{boxInventoryItem}", name = "BoxInventoryItemsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoxInventoryItemsItemJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private BoxInventoryItemService boxInventoryItemService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param boxInventoryItemService
     */
    @Autowired
    public BoxInventoryItemsItemJsonController(BoxInventoryItemService boxInventoryItemService) {
        this.boxInventoryItemService = boxInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return BoxInventoryItemService
     */
    public BoxInventoryItemService getBoxInventoryItemService() {
        return boxInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxInventoryItemService
     */
    public void setBoxInventoryItemService(BoxInventoryItemService boxInventoryItemService) {
        this.boxInventoryItemService = boxInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return BoxInventoryItem
     */
    @ModelAttribute
    public BoxInventoryItem getBoxInventoryItem(@PathVariable("boxInventoryItem") Long id) {
        BoxInventoryItem boxInventoryItem = boxInventoryItemService.findOne(id);
        if (boxInventoryItem == null) {
            throw new NotFoundException(String.format("BoxInventoryItem with identifier '%s' not found", id));
        }
        return boxInventoryItem;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxInventoryItem
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute BoxInventoryItem boxInventoryItem) {
        return ResponseEntity.ok(boxInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxInventoryItem
     * @return UriComponents
     */
    public static UriComponents showURI(BoxInventoryItem boxInventoryItem) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(BoxInventoryItemsItemJsonController.class).show(boxInventoryItem)).buildAndExpand(boxInventoryItem.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedBoxInventoryItem
     * @param boxInventoryItem
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute BoxInventoryItem storedBoxInventoryItem, @Valid @RequestBody BoxInventoryItem boxInventoryItem, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        boxInventoryItem.setId(storedBoxInventoryItem.getId());
        getBoxInventoryItemService().save(boxInventoryItem);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxInventoryItem
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute BoxInventoryItem boxInventoryItem) {
        getBoxInventoryItemService().delete(boxInventoryItem);
        return ResponseEntity.ok().build();
    }
}
