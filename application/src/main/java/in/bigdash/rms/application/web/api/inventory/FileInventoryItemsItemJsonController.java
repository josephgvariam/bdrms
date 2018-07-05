package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;

import in.bigdash.rms.service.api.FileInventoryItemService;
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
@RequestMapping(value = "/api/fileinventoryitems/{fileInventoryItem}", name = "FileInventoryItemsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileInventoryItemsItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private FileInventoryItemService fileInventoryItemService;


    @Autowired
    public FileInventoryItemsItemJsonController(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
    }


    public FileInventoryItemService getFileInventoryItemService() {
        return fileInventoryItemService;
    }


    public void setFileInventoryItemService(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
    }


    @ModelAttribute
    public FileInventoryItem getFileInventoryItem(@PathVariable("fileInventoryItem") Long id) {
        FileInventoryItem fileInventoryItem = fileInventoryItemService.findOne(id);
        if (fileInventoryItem == null) {
            throw new NotFoundException(String.format("FileInventoryItem with identifier '%s' not found", id));
        }
        return fileInventoryItem;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute FileInventoryItem fileInventoryItem) {
        log.debug("show: {}", fileInventoryItem);
        return ResponseEntity.ok(fileInventoryItem);
    }


    public static UriComponents showURI(FileInventoryItem fileInventoryItem) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(FileInventoryItemsItemJsonController.class).show(fileInventoryItem)).buildAndExpand(fileInventoryItem.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute FileInventoryItem storedFileInventoryItem, @Valid @RequestBody FileInventoryItem fileInventoryItem, BindingResult result) {
        log.debug("update: {}", fileInventoryItem);
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", fileInventoryItem, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        fileInventoryItem.setId(storedFileInventoryItem.getId());
        FileInventoryItem updatedFileInventoryItem = getFileInventoryItemService().save(fileInventoryItem);
        log.debug("update saved: {}", updatedFileInventoryItem);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute FileInventoryItem fileInventoryItem) {
        log.debug("delete: {}", fileInventoryItem);
        getFileInventoryItemService().delete(fileInventoryItem);
        return ResponseEntity.ok().build();
    }
}
