package in.bigdash.rms.application.web.api.request;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.inventory.*;
import in.bigdash.rms.model.request.Request;

import in.bigdash.rms.model.request.RequestStatus;
import in.bigdash.rms.service.api.RequestService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;


@RestController
@RequestMapping(value = "/api/requests", name = "RequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RequestService requestService;


    @Autowired
    public RequestsCollectionJsonController(RequestService requestService) {
        this.requestService = requestService;
    }


    public RequestService getRequestService() {
        return requestService;
    }


    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<Request>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<Request> requests = getRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(requests);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RequestsCollectionJsonController.class).list(null, null)).build().encode();
    }

    @PostMapping(value = "/updateLocation", name = "updateLocation")
    public ResponseEntity<?> updateLocation(@RequestParam("requestId") Long requestId, @RequestParam("locationBarcode") String locationBarcode) {
        log.debug("updateLocation: requestId={}, locationBarcode={}", requestId, locationBarcode);

        Request request = requestService.findOne(requestId);

        for (InventoryItem inventoryItem : request.getInventoryItems()){
            inventoryItem.setLocation(locationBarcode);
            inventoryItem.setStatus(InventoryItemStatus.PACKED);
        }

        request.setStatus(RequestStatus.PACKED);

        getRequestService().save(request);

        return ResponseEntity.ok(request);
    }

    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Request request, BindingResult result) {
        log.debug("create: {}", request);
        if (request.getId() != null || request.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", request, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Request newRequest = getRequestService().save(request);
        log.debug("create saved: {}", request);
        UriComponents showURI = RequestsItemJsonController.showURI(newRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Request> requests, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(requests.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(requests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getRequestService().save(requests);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Request> requests, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(requests.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(requests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getRequestService().save(requests);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
