package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.FileInventoryItemService;
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
 * = FileInventoryItemsCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = FileInventoryItem.class, pathPrefix = "/api", type = ControllerType.COLLECTION)
@RooJSON
@RestController
@RequestMapping(value = "/api/fileinventoryitems", name = "FileInventoryItemsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileInventoryItemsCollectionJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private FileInventoryItemService fileInventoryItemService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param fileInventoryItemService
     */
    @Autowired
    public FileInventoryItemsCollectionJsonController(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return FileInventoryItemService
     */
    public FileInventoryItemService getFileInventoryItemService() {
        return fileInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItemService
     */
    public void setFileInventoryItemService(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return ResponseEntity
     */
    @GetMapping(name = "list")
    public ResponseEntity<Page<FileInventoryItem>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<FileInventoryItem> fileInventoryItems = getFileInventoryItemService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(fileInventoryItems);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UriComponents
     */
    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FileInventoryItemsCollectionJsonController.class).list(null, null)).build().encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody FileInventoryItem fileInventoryItem, BindingResult result) {
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItems
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<FileInventoryItem> fileInventoryItems, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getFileInventoryItemService().save(fileInventoryItems);
        return ResponseEntity.created(listURI().toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItems
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<FileInventoryItem> fileInventoryItems, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getFileInventoryItemService().save(fileInventoryItems);
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
        getFileInventoryItemService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
