package in.bigdash.rms.application.web.api.request.destruction;
import in.bigdash.rms.model.request.DestructionRequest;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.DestructionRequestService;
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
 * = DestructionRequestsCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = DestructionRequest.class, pathPrefix = "/api", type = ControllerType.COLLECTION)
@RooJSON
@RestController
@RequestMapping(value = "/api/destructionrequests", name = "DestructionRequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DestructionRequestsCollectionJsonController {

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
    public DestructionRequestsCollectionJsonController(DestructionRequestService destructionRequestService) {
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
     * @param globalSearch
     * @param pageable
     * @return ResponseEntity
     */
    @GetMapping(name = "list")
    public ResponseEntity<Page<DestructionRequest>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<DestructionRequest> destructionRequests = getDestructionRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(destructionRequests);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UriComponents
     */
    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DestructionRequestsCollectionJsonController.class).list(null, null)).build().encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody DestructionRequest destructionRequest, BindingResult result) {
        if (destructionRequest.getId() != null || destructionRequest.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        DestructionRequest newDestructionRequest = getDestructionRequestService().save(destructionRequest);
        UriComponents showURI = DestructionRequestsItemJsonController.showURI(newDestructionRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequests
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<DestructionRequest> destructionRequests, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getDestructionRequestService().save(destructionRequests);
        return ResponseEntity.created(listURI().toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequests
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<DestructionRequest> destructionRequests, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getDestructionRequestService().save(destructionRequests);
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
        getDestructionRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
