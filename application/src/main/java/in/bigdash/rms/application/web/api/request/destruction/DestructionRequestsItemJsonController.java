package in.bigdash.rms.application.web.api.request.destruction;
import in.bigdash.rms.model.request.DestructionRequest;

import in.bigdash.rms.service.api.DestructionRequestService;
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


@RestController
@RequestMapping(value = "/api/destructionrequests/{destructionRequest}", name = "DestructionRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DestructionRequestsItemJsonController {


    private DestructionRequestService destructionRequestService;


    @Autowired
    public DestructionRequestsItemJsonController(DestructionRequestService destructionRequestService) {
        this.destructionRequestService = destructionRequestService;
    }


    public DestructionRequestService getDestructionRequestService() {
        return destructionRequestService;
    }


    public void setDestructionRequestService(DestructionRequestService destructionRequestService) {
        this.destructionRequestService = destructionRequestService;
    }


    @ModelAttribute
    public DestructionRequest getDestructionRequest(@PathVariable("destructionRequest") Long id) {
        DestructionRequest destructionRequest = destructionRequestService.findOne(id);
        if (destructionRequest == null) {
            throw new NotFoundException(String.format("DestructionRequest with identifier '%s' not found", id));
        }
        return destructionRequest;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute DestructionRequest destructionRequest) {
        return ResponseEntity.ok(destructionRequest);
    }


    public static UriComponents showURI(DestructionRequest destructionRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DestructionRequestsItemJsonController.class).show(destructionRequest)).buildAndExpand(destructionRequest.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute DestructionRequest storedDestructionRequest, @Valid @RequestBody DestructionRequest destructionRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        destructionRequest.setId(storedDestructionRequest.getId());
        getDestructionRequestService().save(destructionRequest);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute DestructionRequest destructionRequest) {
        getDestructionRequestService().delete(destructionRequest);
        return ResponseEntity.ok().build();
    }
}
