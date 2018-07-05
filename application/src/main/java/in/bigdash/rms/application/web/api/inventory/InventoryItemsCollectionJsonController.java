package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.application.security.JpaUserDetails;
import in.bigdash.rms.model.inventory.InventoryItem;

import in.bigdash.rms.service.api.InventoryItemService;
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
@RequestMapping(value = "/api/inventoryitems", name = "InventoryItemsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryItemsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private InventoryItemService inventoryItemService;

    @Autowired
    Validator validator;


    @Autowired
    public InventoryItemsCollectionJsonController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }


    public InventoryItemService getInventoryItemService() {
        return inventoryItemService;
    }


    public void setInventoryItemService(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<InventoryItem>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<InventoryItem> inventoryItems = getInventoryItemService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(inventoryItems);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(InventoryItemsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody InventoryItem inventoryItem, BindingResult result, Authentication authentication) {
        log.debug("create: {}", inventoryItem);

        inventoryItem.setUserCreated(((JpaUserDetails)authentication.getPrincipal()).getUser());

        validator.validate(inventoryItem, result);

        if (inventoryItem.getId() != null || inventoryItem.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", inventoryItem, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        InventoryItem newInventoryItem = getInventoryItemService().save(inventoryItem);
        log.debug("create saved: {}", inventoryItem);
        UriComponents showURI = InventoryItemsItemJsonController.showURI(newInventoryItem);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<InventoryItem> inventoryItems, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(inventoryItems.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(inventoryItems.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getInventoryItemService().save(inventoryItems);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<InventoryItem> inventoryItems, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(inventoryItems.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(inventoryItems.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getInventoryItemService().save(inventoryItems);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getInventoryItemService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
