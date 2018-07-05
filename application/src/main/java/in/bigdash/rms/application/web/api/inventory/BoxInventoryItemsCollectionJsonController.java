package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.application.security.JpaUserDetails;
import in.bigdash.rms.model.inventory.BoxInventoryItem;

import in.bigdash.rms.service.api.BoxInventoryItemService;
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
@RequestMapping(value = "/api/boxinventoryitems", name = "BoxInventoryItemsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoxInventoryItemsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BoxInventoryItemService boxInventoryItemService;

    @Autowired
    Validator validator;


    @Autowired
    public BoxInventoryItemsCollectionJsonController(BoxInventoryItemService boxInventoryItemService) {
        this.boxInventoryItemService = boxInventoryItemService;
    }


    public BoxInventoryItemService getBoxInventoryItemService() {
        return boxInventoryItemService;
    }


    public void setBoxInventoryItemService(BoxInventoryItemService boxInventoryItemService) {
        this.boxInventoryItemService = boxInventoryItemService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<BoxInventoryItem>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<BoxInventoryItem> boxInventoryItems = getBoxInventoryItemService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(boxInventoryItems);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(BoxInventoryItemsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@RequestBody BoxInventoryItem boxInventoryItem, BindingResult result, Authentication authentication) {
        log.debug("create: {}", boxInventoryItem);

        boxInventoryItem.setUserCreated(((JpaUserDetails)authentication.getPrincipal()).getUser());

        validator.validate(boxInventoryItem, result);

        if (boxInventoryItem.getId() != null || boxInventoryItem.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", boxInventoryItem, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        BoxInventoryItem newBoxInventoryItem = getBoxInventoryItemService().save(boxInventoryItem);
        log.debug("create saved: {}", boxInventoryItem);
        UriComponents showURI = BoxInventoryItemsItemJsonController.showURI(newBoxInventoryItem);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<BoxInventoryItem> boxInventoryItems, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(boxInventoryItems.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(boxInventoryItems.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getBoxInventoryItemService().save(boxInventoryItems);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<BoxInventoryItem> boxInventoryItems, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(boxInventoryItems.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(boxInventoryItems.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getBoxInventoryItemService().save(boxInventoryItems);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getBoxInventoryItemService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
