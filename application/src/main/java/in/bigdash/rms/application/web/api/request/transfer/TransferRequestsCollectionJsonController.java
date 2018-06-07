package in.bigdash.rms.application.web.api.request.transfer;
import in.bigdash.rms.model.request.TransferRequest;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.TransferRequestService;
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
 * = TransferRequestsCollectionJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = TransferRequest.class, pathPrefix = "/api", type = ControllerType.COLLECTION)
@RooJSON
@RestController
@RequestMapping(value = "/api/transferrequests", name = "TransferRequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransferRequestsCollectionJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private TransferRequestService transferRequestService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param transferRequestService
     */
    @Autowired
    public TransferRequestsCollectionJsonController(TransferRequestService transferRequestService) {
        this.transferRequestService = transferRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return TransferRequestService
     */
    public TransferRequestService getTransferRequestService() {
        return transferRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequestService
     */
    public void setTransferRequestService(TransferRequestService transferRequestService) {
        this.transferRequestService = transferRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return ResponseEntity
     */
    @GetMapping(name = "list")
    public ResponseEntity<Page<TransferRequest>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<TransferRequest> transferRequests = getTransferRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(transferRequests);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UriComponents
     */
    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(TransferRequestsCollectionJsonController.class).list(null, null)).build().encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequest
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody TransferRequest transferRequest, BindingResult result) {
        if (transferRequest.getId() != null || transferRequest.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        TransferRequest newTransferRequest = getTransferRequestService().save(transferRequest);
        UriComponents showURI = TransferRequestsItemJsonController.showURI(newTransferRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequests
     * @param result
     * @return ResponseEntity
     */
    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<TransferRequest> transferRequests, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getTransferRequestService().save(transferRequests);
        return ResponseEntity.created(listURI().toUri()).build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequests
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<TransferRequest> transferRequests, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getTransferRequestService().save(transferRequests);
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
        getTransferRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
