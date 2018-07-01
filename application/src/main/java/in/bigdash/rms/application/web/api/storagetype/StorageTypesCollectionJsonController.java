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
import java.util.Collection;


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
        Page<StorageType> storageTypes = getStorageTypeService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(storageTypes);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(StorageTypesCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody StorageType storageType, BindingResult result) {
        if (storageType.getId() != null || storageType.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        StorageType newStorageType = getStorageTypeService().save(storageType);
        UriComponents showURI = StorageTypesItemJsonController.showURI(newStorageType);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<StorageType> storageTypes, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getStorageTypeService().save(storageTypes);
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<StorageType> storageTypes, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getStorageTypeService().save(storageTypes);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        getStorageTypeService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
