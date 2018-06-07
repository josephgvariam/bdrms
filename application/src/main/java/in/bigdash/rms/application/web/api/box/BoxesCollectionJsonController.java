package in.bigdash.rms.application.web.api.box;
import in.bigdash.rms.model.Box;

import in.bigdash.rms.service.api.BoxService;
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


@RestController
@RequestMapping(value = "/api/boxes", name = "BoxesCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoxesCollectionJsonController {


    private BoxService boxService;


    @Autowired
    public BoxesCollectionJsonController(BoxService boxService) {
        this.boxService = boxService;
    }


    public BoxService getBoxService() {
        return boxService;
    }


    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<Box>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<Box> boxes = getBoxService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(boxes);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(BoxesCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Box box, BindingResult result) {
        if (box.getId() != null || box.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Box newBox = getBoxService().save(box);
        UriComponents showURI = BoxesItemJsonController.showURI(newBox);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Box> boxes, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getBoxService().save(boxes);
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Box> boxes, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getBoxService().save(boxes);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        getBoxService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
