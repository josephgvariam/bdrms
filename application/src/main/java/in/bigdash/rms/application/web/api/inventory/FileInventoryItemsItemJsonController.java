package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.FileInventoryItemService;
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
 * = FileInventoryItemsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = FileInventoryItem.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/fileinventoryitems/{fileInventoryItem}", name = "FileInventoryItemsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileInventoryItemsItemJsonController {

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
    public FileInventoryItemsItemJsonController(FileInventoryItemService fileInventoryItemService) {
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
     * @param id
     * @return FileInventoryItem
     */
    @ModelAttribute
    public FileInventoryItem getFileInventoryItem(@PathVariable("fileInventoryItem") Long id) {
        FileInventoryItem fileInventoryItem = fileInventoryItemService.findOne(id);
        if (fileInventoryItem == null) {
            throw new NotFoundException(String.format("FileInventoryItem with identifier '%s' not found", id));
        }
        return fileInventoryItem;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute FileInventoryItem fileInventoryItem) {
        return ResponseEntity.ok(fileInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     * @return UriComponents
     */
    public static UriComponents showURI(FileInventoryItem fileInventoryItem) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FileInventoryItemsItemJsonController.class).show(fileInventoryItem)).buildAndExpand(fileInventoryItem.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedFileInventoryItem
     * @param fileInventoryItem
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute FileInventoryItem storedFileInventoryItem, @Valid @RequestBody FileInventoryItem fileInventoryItem, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        fileInventoryItem.setId(storedFileInventoryItem.getId());
        getFileInventoryItemService().save(fileInventoryItem);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute FileInventoryItem fileInventoryItem) {
        getFileInventoryItemService().delete(fileInventoryItem);
        return ResponseEntity.ok().build();
    }
}
