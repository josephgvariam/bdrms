package in.bigdash.rms.application.web.api.shelf;
import in.bigdash.rms.model.Shelf;

import in.bigdash.rms.service.api.ShelfService;
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


@RestController
@RequestMapping(value = "/shelves", name = "ShelvesCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShelvesCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ShelfService shelfService;


    @Autowired
    public ShelvesCollectionJsonController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }


    public ShelfService getShelfService() {
        return shelfService;
    }


    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<Shelf>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<Shelf> shelves = getShelfService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(shelves);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(ShelvesCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Shelf shelf, BindingResult result) {
        log.debug("create: {}", shelf);
        if (shelf.getId() != null || shelf.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", shelf, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Shelf newShelf = getShelfService().save(shelf);
        log.debug("create saved: {}", shelf);
        UriComponents showURI = ShelvesItemJsonController.showURI(newShelf);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Shelf> shelves, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(shelves.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(shelves.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getShelfService().save(shelves);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Shelf> shelves, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(shelves.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(shelves.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getShelfService().save(shelves);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getShelfService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
