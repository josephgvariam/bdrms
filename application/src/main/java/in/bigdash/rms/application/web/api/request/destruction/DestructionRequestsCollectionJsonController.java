package in.bigdash.rms.application.web.api.request.destruction;
import in.bigdash.rms.model.request.DestructionRequest;

import in.bigdash.rms.service.api.DestructionRequestService;
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
@RequestMapping(value = "/api/destructionrequests", name = "DestructionRequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DestructionRequestsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DestructionRequestService destructionRequestService;


    @Autowired
    public DestructionRequestsCollectionJsonController(DestructionRequestService destructionRequestService) {
        this.destructionRequestService = destructionRequestService;
    }


    public DestructionRequestService getDestructionRequestService() {
        return destructionRequestService;
    }


    public void setDestructionRequestService(DestructionRequestService destructionRequestService) {
        this.destructionRequestService = destructionRequestService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<DestructionRequest>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<DestructionRequest> destructionRequests = getDestructionRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(destructionRequests);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DestructionRequestsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody DestructionRequest destructionRequest, BindingResult result) {
        log.debug("create: {}", destructionRequest);
        if (destructionRequest.getId() != null || destructionRequest.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", destructionRequest, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        DestructionRequest newDestructionRequest = getDestructionRequestService().save(destructionRequest);
        log.debug("create saved: {}", destructionRequest);
        UriComponents showURI = DestructionRequestsItemJsonController.showURI(newDestructionRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<DestructionRequest> destructionRequests, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(destructionRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(destructionRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getDestructionRequestService().save(destructionRequests);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<DestructionRequest> destructionRequests, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(destructionRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(destructionRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getDestructionRequestService().save(destructionRequests);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getDestructionRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
