package in.bigdash.rms.application.web.api.request.retrieval;
import in.bigdash.rms.model.request.RetrievalRequest;

import in.bigdash.rms.service.api.RetrievalRequestService;
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
@RequestMapping(value = "/api/retrievalrequests", name = "RetrievalRequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RetrievalRequestsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RetrievalRequestService retrievalRequestService;


    @Autowired
    public RetrievalRequestsCollectionJsonController(RetrievalRequestService retrievalRequestService) {
        this.retrievalRequestService = retrievalRequestService;
    }


    public RetrievalRequestService getRetrievalRequestService() {
        return retrievalRequestService;
    }


    public void setRetrievalRequestService(RetrievalRequestService retrievalRequestService) {
        this.retrievalRequestService = retrievalRequestService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<RetrievalRequest>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<RetrievalRequest> retrievalRequests = getRetrievalRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(retrievalRequests);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RetrievalRequestsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody RetrievalRequest retrievalRequest, BindingResult result) {
        log.debug("create: {}", retrievalRequest);
        if (retrievalRequest.getId() != null || retrievalRequest.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", retrievalRequest, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        RetrievalRequest newRetrievalRequest = getRetrievalRequestService().save(retrievalRequest);
        log.debug("create saved: {}", retrievalRequest);
        UriComponents showURI = RetrievalRequestsItemJsonController.showURI(newRetrievalRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<RetrievalRequest> retrievalRequests, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(retrievalRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(retrievalRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getRetrievalRequestService().save(retrievalRequests);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<RetrievalRequest> retrievalRequests, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(retrievalRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(retrievalRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getRetrievalRequestService().save(retrievalRequests);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getRetrievalRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
