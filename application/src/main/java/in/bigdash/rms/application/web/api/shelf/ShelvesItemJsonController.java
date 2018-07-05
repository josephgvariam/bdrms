package in.bigdash.rms.application.web.api.shelf;
import in.bigdash.rms.model.Shelf;

import in.bigdash.rms.service.api.ShelfService;
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
@RequestMapping(value = "/shelves/{shelf}", name = "ShelvesItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShelvesItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ShelfService shelfService;


    @Autowired
    public ShelvesItemJsonController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }


    public ShelfService getShelfService() {
        return shelfService;
    }


    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
    }


    @ModelAttribute
    public Shelf getShelf(@PathVariable("shelf") Long id) {
        Shelf shelf = shelfService.findOne(id);
        if (shelf == null) {
            throw new NotFoundException(String.format("Shelf with identifier '%s' not found", id));
        }
        return shelf;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Shelf shelf) {
        log.debug("show: {}", shelf);
        return ResponseEntity.ok(shelf);
    }


    public static UriComponents showURI(Shelf shelf) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(ShelvesItemJsonController.class).show(shelf)).buildAndExpand(shelf.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Shelf storedShelf, @Valid @RequestBody Shelf shelf, BindingResult result) {
        log.debug("update: {}", shelf);
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", shelf, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        shelf.setId(storedShelf.getId());
        Shelf updatedShelf = getShelfService().save(shelf);
        log.debug("update saved: {}", updatedShelf);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Shelf shelf) {
        log.debug("delete: {}", shelf);
        getShelfService().delete(shelf);
        return ResponseEntity.ok().build();
    }
}
