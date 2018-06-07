package in.bigdash.rms.application.web.api.shelf;
import in.bigdash.rms.model.Shelf;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.ShelfService;
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
 * = ShelvesItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Shelf.class, type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/shelves/{shelf}", name = "ShelvesItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShelvesItemJsonController {

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
    public ShelvesItemJsonController(ShelfService shelfService) {
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
     * @param id
     * @return Shelf
     */
    @ModelAttribute
    public Shelf getShelf(@PathVariable("shelf") Long id) {
        Shelf shelf = shelfService.findOne(id);
        if (shelf == null) {
            throw new NotFoundException(String.format("Shelf with identifier '%s' not found", id));
        }
        return shelf;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Shelf shelf) {
        return ResponseEntity.ok(shelf);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @return UriComponents
     */
    public static UriComponents showURI(Shelf shelf) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(ShelvesItemJsonController.class).show(shelf)).buildAndExpand(shelf.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedShelf
     * @param shelf
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Shelf storedShelf, @Valid @RequestBody Shelf shelf, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        shelf.setId(storedShelf.getId());
        getShelfService().save(shelf);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Shelf shelf) {
        getShelfService().delete(shelf);
        return ResponseEntity.ok().build();
    }
}
