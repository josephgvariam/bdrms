package in.bigdash.rms.application.web.api.box;
import in.bigdash.rms.model.Box;

import in.bigdash.rms.service.api.BoxService;
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
@RequestMapping(value = "/api/boxes/{box}", name = "BoxesItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoxesItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BoxService boxService;


    @Autowired
    public BoxesItemJsonController(BoxService boxService) {
        this.boxService = boxService;
    }


    public BoxService getBoxService() {
        return boxService;
    }


    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }


    @ModelAttribute
    public Box getBox(@PathVariable("box") Long id) {
        Box box = boxService.findOne(id);
        if (box == null) {
            throw new NotFoundException(String.format("Box with identifier '%s' not found", id));
        }
        return box;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Box box) {
        return ResponseEntity.ok(box);
    }


    public static UriComponents showURI(Box box) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(BoxesItemJsonController.class).show(box)).buildAndExpand(box.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Box storedBox, @Valid @RequestBody Box box, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        box.setId(storedBox.getId());
        getBoxService().save(box);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Box box) {
        getBoxService().delete(box);
        return ResponseEntity.ok().build();
    }
}
