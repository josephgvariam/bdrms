package in.bigdash.rms.application.web.api.request.refiling;
import in.bigdash.rms.model.request.RefilingRequest;

import in.bigdash.rms.service.api.RefilingRequestService;
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
@RequestMapping(value = "/api/refilingrequests", name = "RefilingRequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RefilingRequestsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RefilingRequestService refilingRequestService;


    @Autowired
    public RefilingRequestsCollectionJsonController(RefilingRequestService refilingRequestService) {
        this.refilingRequestService = refilingRequestService;
    }


    public RefilingRequestService getRefilingRequestService() {
        return refilingRequestService;
    }


    public void setRefilingRequestService(RefilingRequestService refilingRequestService) {
        this.refilingRequestService = refilingRequestService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<RefilingRequest>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<RefilingRequest> refilingRequests = getRefilingRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(refilingRequests);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RefilingRequestsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody RefilingRequest refilingRequest, BindingResult result) {
        log.debug("create: {}", refilingRequest);
        if (refilingRequest.getId() != null || refilingRequest.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", refilingRequest, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        RefilingRequest newRefilingRequest = getRefilingRequestService().save(refilingRequest);
        log.debug("create saved: {}", refilingRequest);
        UriComponents showURI = RefilingRequestsItemJsonController.showURI(newRefilingRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<RefilingRequest> refilingRequests, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(refilingRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(refilingRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getRefilingRequestService().save(refilingRequests);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<RefilingRequest> refilingRequests, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(refilingRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(refilingRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getRefilingRequestService().save(refilingRequests);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getRefilingRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
