package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.BoxInventoryItem;

import in.bigdash.rms.service.api.BoxInventoryItemService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/api/boxinventoryitems/{boxInventoryItem}", name = "BoxInventoryItemsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoxInventoryItemsItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BoxInventoryItemService boxInventoryItemService;


    @Autowired
    public BoxInventoryItemsItemJsonController(BoxInventoryItemService boxInventoryItemService) {
        this.boxInventoryItemService = boxInventoryItemService;
    }


    public BoxInventoryItemService getBoxInventoryItemService() {
        return boxInventoryItemService;
    }


    public void setBoxInventoryItemService(BoxInventoryItemService boxInventoryItemService) {
        this.boxInventoryItemService = boxInventoryItemService;
    }


    @ModelAttribute
    public BoxInventoryItem getBoxInventoryItem(@PathVariable("boxInventoryItem") Long id) {
        BoxInventoryItem boxInventoryItem = boxInventoryItemService.findOne(id);
        if (boxInventoryItem == null) {
            throw new NotFoundException(String.format("BoxInventoryItem with identifier '%s' not found", id));
        }
        return boxInventoryItem;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute BoxInventoryItem boxInventoryItem) {
        log.debug("show: {}", boxInventoryItem);
        return ResponseEntity.ok(boxInventoryItem);
    }


    public static UriComponents showURI(BoxInventoryItem boxInventoryItem) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(BoxInventoryItemsItemJsonController.class).show(boxInventoryItem)).buildAndExpand(boxInventoryItem.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute BoxInventoryItem storedBoxInventoryItem, @Valid @RequestBody BoxInventoryItem boxInventoryItem, BindingResult result) {
        log.debug("update: {}", boxInventoryItem);
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", boxInventoryItem, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        boxInventoryItem.setId(storedBoxInventoryItem.getId());
        BoxInventoryItem updatedBoxInventoryItem = getBoxInventoryItemService().save(boxInventoryItem);
        log.debug("update saved: {}", updatedBoxInventoryItem);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute BoxInventoryItem boxInventoryItem) {
        log.debug("delete: {}", boxInventoryItem);
        getBoxInventoryItemService().delete(boxInventoryItem);
        return ResponseEntity.ok().build();
    }
}
