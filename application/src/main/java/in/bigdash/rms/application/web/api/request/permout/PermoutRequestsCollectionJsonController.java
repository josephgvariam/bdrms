package in.bigdash.rms.application.web.api.request.permout;
import in.bigdash.rms.model.request.PermoutRequest;

import in.bigdash.rms.service.api.PermoutRequestService;
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
@RequestMapping(value = "/api/permoutrequests", name = "PermoutRequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermoutRequestsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private PermoutRequestService permoutRequestService;


    @Autowired
    public PermoutRequestsCollectionJsonController(PermoutRequestService permoutRequestService) {
        this.permoutRequestService = permoutRequestService;
    }


    public PermoutRequestService getPermoutRequestService() {
        return permoutRequestService;
    }


    public void setPermoutRequestService(PermoutRequestService permoutRequestService) {
        this.permoutRequestService = permoutRequestService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<PermoutRequest>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<PermoutRequest> permoutRequests = getPermoutRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(permoutRequests);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(PermoutRequestsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody PermoutRequest permoutRequest, BindingResult result) {
        log.debug("create: {}", permoutRequest);
        if (permoutRequest.getId() != null || permoutRequest.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", permoutRequest, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        PermoutRequest newPermoutRequest = getPermoutRequestService().save(permoutRequest);
        log.debug("create saved: {}", permoutRequest);
        UriComponents showURI = PermoutRequestsItemJsonController.showURI(newPermoutRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<PermoutRequest> permoutRequests, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(permoutRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(permoutRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getPermoutRequestService().save(permoutRequests);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<PermoutRequest> permoutRequests, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(permoutRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(permoutRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getPermoutRequestService().save(permoutRequests);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getPermoutRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
