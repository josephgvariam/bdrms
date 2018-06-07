package in.bigdash.rms.application.web.api.request.destruction;
import in.bigdash.rms.model.request.DestructionRequest;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
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

/**
 * = DestructionRequestsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = DestructionRequest.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/destructionrequests/{destructionRequest}", name = "DestructionRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DestructionRequestsItemJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DestructionRequestService destructionRequestService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param destructionRequestService
     */
    @Autowired
    public DestructionRequestsItemJsonController(DestructionRequestService destructionRequestService) {
        this.destructionRequestService = destructionRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DestructionRequestService
     */
    public DestructionRequestService getDestructionRequestService() {
        return destructionRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequestService
     */
    public void setDestructionRequestService(DestructionRequestService destructionRequestService) {
        this.destructionRequestService = destructionRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return DestructionRequest
     */
    @ModelAttribute
    public DestructionRequest getDestructionRequest(@PathVariable("destructionRequest") Long id) {
        DestructionRequest destructionRequest = destructionRequestService.findOne(id);
        if (destructionRequest == null) {
            throw new NotFoundException(String.format("DestructionRequest with identifier '%s' not found", id));
        }
        return destructionRequest;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute DestructionRequest destructionRequest) {
        return ResponseEntity.ok(destructionRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     * @return UriComponents
     */
    public static UriComponents showURI(DestructionRequest destructionRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DestructionRequestsItemJsonController.class).show(destructionRequest)).buildAndExpand(destructionRequest.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedDestructionRequest
     * @param destructionRequest
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute DestructionRequest storedDestructionRequest, @Valid @RequestBody DestructionRequest destructionRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        destructionRequest.setId(storedDestructionRequest.getId());
        getDestructionRequestService().save(destructionRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute DestructionRequest destructionRequest) {
        getDestructionRequestService().delete(destructionRequest);
        return ResponseEntity.ok().build();
    }
}
