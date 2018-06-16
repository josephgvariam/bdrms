package in.bigdash.rms.application.web.api.storagetype;

import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.service.api.StorageTypeService;
import io.springlets.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/storagetypes/{storageType}", name = "StorageTypesItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageTypesItemJsonController {


    private StorageTypeService storageTypeService;


    @Autowired
    public StorageTypesItemJsonController(StorageTypeService storageTypeService) {
        this.storageTypeService = storageTypeService;
    }


    public StorageTypeService getStorageTypeService() {
        return storageTypeService;
    }


    public void setStorageTypeService(StorageTypeService storageTypeService) {
        this.storageTypeService = storageTypeService;
    }


    @ModelAttribute
    public StorageType getStorageType(@PathVariable("storageType") Long id) {
        StorageType storageType = storageTypeService.findOne(id);
        if (storageType == null) {
            throw new NotFoundException(String.format("StorageType with identifier '%s' not found", id));
        }
        return storageType;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute StorageType storageType) {
        return ResponseEntity.ok(storageType);
    }


    public static UriComponents showURI(StorageType storageType) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(StorageTypesItemJsonController.class).show(storageType)).buildAndExpand(storageType.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute StorageType storedStorageType, @Valid @RequestBody StorageType storageType, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        storageType.setId(storedStorageType.getId());
        getStorageTypeService().save(storageType);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute StorageType storageType) {
        getStorageTypeService().delete(storageType);
        return ResponseEntity.ok().build();
    }
}
