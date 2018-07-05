package in.bigdash.rms.application.web.api.storagetype;

import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.service.api.StorageTypeService;
import io.springlets.data.domain.GlobalSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping(value = "/api/storagetypes", name = "StorageTypesCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageTypesCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private StorageTypeService storageTypeService;


    @Autowired
    public StorageTypesCollectionJsonController(StorageTypeService storageTypeService) {
        this.storageTypeService = storageTypeService;
    }


    public StorageTypeService getStorageTypeService() {
        return storageTypeService;
    }


    public void setStorageTypeService(StorageTypeService storageTypeService) {
        this.storageTypeService = storageTypeService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<StorageType>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<StorageType> storageTypes = getStorageTypeService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(storageTypes);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(StorageTypesCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody StorageType storageType, BindingResult result) {
        log.debug("create: {}", storageType);
        if (storageType.getId() != null || storageType.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", storageType, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        StorageType newStorageType = getStorageTypeService().save(storageType);
        log.debug("create saved: {}", storageType);
        UriComponents showURI = StorageTypesItemJsonController.showURI(newStorageType);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<StorageType> storageTypes, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(storageTypes.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(storageTypes.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getStorageTypeService().save(storageTypes);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<StorageType> storageTypes, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(storageTypes.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(storageTypes.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getStorageTypeService().save(storageTypes);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getStorageTypeService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
