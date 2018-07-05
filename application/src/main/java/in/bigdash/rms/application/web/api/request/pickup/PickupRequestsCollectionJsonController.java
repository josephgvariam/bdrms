package in.bigdash.rms.application.web.api.request.pickup;
import in.bigdash.rms.application.security.JpaUserDetails;
import in.bigdash.rms.model.request.PickupRequest;

import in.bigdash.rms.model.request.RequestStatus;
import in.bigdash.rms.service.api.PickupRequestService;
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
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
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
@RequestMapping(value = "/api/pickuprequests", name = "PickupRequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class PickupRequestsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private PickupRequestService pickupRequestService;

    @Autowired
    Validator validator;


    @Autowired
    public PickupRequestsCollectionJsonController(PickupRequestService pickupRequestService) {
        this.pickupRequestService = pickupRequestService;
    }


    public PickupRequestService getPickupRequestService() {
        return pickupRequestService;
    }


    public void setPickupRequestService(PickupRequestService pickupRequestService) {
        this.pickupRequestService = pickupRequestService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<PickupRequest>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<PickupRequest> pickupRequests = getPickupRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(pickupRequests);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(PickupRequestsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@RequestBody PickupRequest pickupRequest, BindingResult result, Authentication authentication) {
        log.debug("create: {}", pickupRequest);
        pickupRequest.setUserCreated(((JpaUserDetails)authentication.getPrincipal()).getUser());
        pickupRequest.setStatus(RequestStatus.OPEN);

        validator.validate(pickupRequest, result);

        if (pickupRequest.getId() != null || pickupRequest.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", pickupRequest, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        PickupRequest newPickupRequest = getPickupRequestService().save(pickupRequest);
        log.debug("create saved: {}", pickupRequest);

//        UriComponents showURI = PickupRequestsItemJsonController.showURI(newPickupRequest);
//        return ResponseEntity.created(showURI.toUri()).build();

        return ResponseEntity.ok(newPickupRequest);
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<PickupRequest> pickupRequests, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(pickupRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(pickupRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getPickupRequestService().save(pickupRequests);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<PickupRequest> pickupRequests, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(pickupRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(pickupRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getPickupRequestService().save(pickupRequests);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getPickupRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
