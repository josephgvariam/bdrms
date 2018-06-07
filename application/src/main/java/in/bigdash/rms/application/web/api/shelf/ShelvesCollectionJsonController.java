package in.bigdash.rms.application.web.api.shelf;
import in.bigdash.rms.model.Shelf;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.ShelfService;
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
 * = ShelvesCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Shelf.class, type = ControllerType.COLLECTION)
@RooJSON
@RestController
@RequestMapping(value = "/shelves", name = "ShelvesCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShelvesCollectionJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ShelfService shelfService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param shelfService
     */
    @Autowired
    public ShelvesCollectionJsonController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ShelfService
     */
    public ShelfService getShelfService() {
        return shelfService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelfService
     */
    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return ResponseEntity
     */
    @GetMapping(name = "list")
    public ResponseEntity<Page<Shelf>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<Shelf> shelves = getShelfService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(shelves);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UriComponents
     */
    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(ShelvesCollectionJsonController.class).list(null, null)).build().encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Shelf shelf, BindingResult result) {
        if (shelf.getId() != null || shelf.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Shelf newShelf = getShelfService().save(shelf);
        UriComponents showURI = ShelvesItemJsonController.showURI(newShelf);
        return ResponseEntity.created(showURI.toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelves
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Shelf> shelves, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getShelfService().save(shelves);
        return ResponseEntity.created(listURI().toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelves
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Shelf> shelves, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getShelfService().save(shelves);
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
        getShelfService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
