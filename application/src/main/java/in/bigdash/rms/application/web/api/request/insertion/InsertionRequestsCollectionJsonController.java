package in.bigdash.rms.application.web.api.request.insertion;
import in.bigdash.rms.model.request.InsertionRequest;

import in.bigdash.rms.service.api.InsertionRequestService;
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
@RequestMapping(value = "/api/insertionrequests", name = "InsertionRequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class InsertionRequestsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private InsertionRequestService insertionRequestService;


    @Autowired
    public InsertionRequestsCollectionJsonController(InsertionRequestService insertionRequestService) {
        this.insertionRequestService = insertionRequestService;
    }


    public InsertionRequestService getInsertionRequestService() {
        return insertionRequestService;
    }


    public void setInsertionRequestService(InsertionRequestService insertionRequestService) {
        this.insertionRequestService = insertionRequestService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<InsertionRequest>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<InsertionRequest> insertionRequests = getInsertionRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(insertionRequests);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(InsertionRequestsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody InsertionRequest insertionRequest, BindingResult result) {
        log.debug("create: {}", insertionRequest);
        if (insertionRequest.getId() != null || insertionRequest.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", insertionRequest, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        InsertionRequest newInsertionRequest = getInsertionRequestService().save(insertionRequest);
        log.debug("create saved: {}", insertionRequest);
        UriComponents showURI = InsertionRequestsItemJsonController.showURI(newInsertionRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<InsertionRequest> insertionRequests, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(insertionRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(insertionRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getInsertionRequestService().save(insertionRequests);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<InsertionRequest> insertionRequests, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(insertionRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(insertionRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getInsertionRequestService().save(insertionRequests);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getInsertionRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
