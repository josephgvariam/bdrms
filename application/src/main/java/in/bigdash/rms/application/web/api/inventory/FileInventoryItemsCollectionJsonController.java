package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.application.security.JpaUserDetails;
import in.bigdash.rms.model.inventory.FileInventoryItem;

import in.bigdash.rms.service.api.FileInventoryItemService;
import io.springlets.data.domain.GlobalSearch;
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
@RequestMapping(value = "/api/fileinventoryitems", name = "FileInventoryItemsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileInventoryItemsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private FileInventoryItemService fileInventoryItemService;


    @Autowired
    Validator validator;


    @Autowired
    public FileInventoryItemsCollectionJsonController(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
    }


    public FileInventoryItemService getFileInventoryItemService() {
        return fileInventoryItemService;
    }


    public void setFileInventoryItemService(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<FileInventoryItem>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<FileInventoryItem> fileInventoryItems = getFileInventoryItemService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(fileInventoryItems);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FileInventoryItemsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody FileInventoryItem fileInventoryItem, BindingResult result, Authentication authentication) {

        fileInventoryItem.setUserCreated(((JpaUserDetails)authentication.getPrincipal()).getUser());

        validator.validate(fileInventoryItem, result);

        if (fileInventoryItem.getId() != null || fileInventoryItem.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        FileInventoryItem newFileInventoryItem = getFileInventoryItemService().save(fileInventoryItem);
        UriComponents showURI = FileInventoryItemsItemJsonController.showURI(newFileInventoryItem);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<FileInventoryItem> fileInventoryItems, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getFileInventoryItemService().save(fileInventoryItems);
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<FileInventoryItem> fileInventoryItems, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getFileInventoryItemService().save(fileInventoryItems);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        getFileInventoryItemService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
