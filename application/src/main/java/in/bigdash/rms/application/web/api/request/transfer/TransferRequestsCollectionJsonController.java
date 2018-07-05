package in.bigdash.rms.application.web.api.request.transfer;
import in.bigdash.rms.model.request.TransferRequest;

import in.bigdash.rms.service.api.TransferRequestService;
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
@RequestMapping(value = "/api/transferrequests", name = "TransferRequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransferRequestsCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private TransferRequestService transferRequestService;


    @Autowired
    public TransferRequestsCollectionJsonController(TransferRequestService transferRequestService) {
        this.transferRequestService = transferRequestService;
    }


    public TransferRequestService getTransferRequestService() {
        return transferRequestService;
    }


    public void setTransferRequestService(TransferRequestService transferRequestService) {
        this.transferRequestService = transferRequestService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<TransferRequest>> list(GlobalSearch globalSearch, Pageable pageable) {
        log.debug("list");
        Page<TransferRequest> transferRequests = getTransferRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(transferRequests);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(TransferRequestsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody TransferRequest transferRequest, BindingResult result) {
        log.debug("create: {}", transferRequest);
        if (transferRequest.getId() != null || transferRequest.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            log.debug("create {} has errors: {}", transferRequest, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        TransferRequest newTransferRequest = getTransferRequestService().save(transferRequest);
        log.debug("create saved: {}", transferRequest);
        UriComponents showURI = TransferRequestsItemJsonController.showURI(newTransferRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<TransferRequest> transferRequests, BindingResult result) {
        log.debug("createBatch: {}", Arrays.toString(transferRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("createBatch has errors: {}", result.getAllErrors(), Arrays.toString(transferRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getTransferRequestService().save(transferRequests);
        log.debug("createBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<TransferRequest> transferRequests, BindingResult result) {
        log.debug("updateBatch: {}", Arrays.toString(transferRequests.toArray()));
        if (result.hasErrors()) {
            log.debug("updateBatch has errors: {}", result.getAllErrors(), Arrays.toString(transferRequests.toArray()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        Collection savedBatch = getTransferRequestService().save(transferRequests);
        log.debug("updateBatch saved: {}", Arrays.toString(savedBatch.toArray()));
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        log.debug("deleteBatch: {}", Arrays.toString(ids.toArray()));
        getTransferRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
