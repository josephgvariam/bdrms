package in.bigdash.rms.application.web.api.request.transfer;
import in.bigdash.rms.model.request.TransferRequest;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.TransferRequestService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

/**
 * = TransferRequestsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = TransferRequest.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/transferrequests/{transferRequest}", name = "TransferRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransferRequestsItemJsonController {

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
    public TransferRequestsItemJsonController(TransferRequestService transferRequestService) {
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
     * @param id
     * @return TransferRequest
     */
    @ModelAttribute
    public TransferRequest getTransferRequest(@PathVariable("transferRequest") Long id) {
        TransferRequest transferRequest = transferRequestService.findOne(id);
        if (transferRequest == null) {
            throw new NotFoundException(String.format("TransferRequest with identifier '%s' not found", id));
        }
        return transferRequest;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequest
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute TransferRequest transferRequest) {
        return ResponseEntity.ok(transferRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequest
     * @return UriComponents
     */
    public static UriComponents showURI(TransferRequest transferRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(TransferRequestsItemJsonController.class).show(transferRequest)).buildAndExpand(transferRequest.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedTransferRequest
     * @param transferRequest
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute TransferRequest storedTransferRequest, @Valid @RequestBody TransferRequest transferRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        transferRequest.setId(storedTransferRequest.getId());
        getTransferRequestService().save(transferRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequest
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute TransferRequest transferRequest) {
        getTransferRequestService().delete(transferRequest);
        return ResponseEntity.ok().build();
    }
}
