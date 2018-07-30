package in.bigdash.rms.application.web.api.request.permout;
import in.bigdash.rms.model.request.PermoutRequest;

import in.bigdash.rms.model.request.RequestStatus;
import in.bigdash.rms.service.api.OtpService;
import in.bigdash.rms.service.api.PermoutRequestService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@RestController
@RequestMapping(value = "/api/permoutrequests/{permoutRequest}", name = "PermoutRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermoutRequestsItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private PermoutRequestService permoutRequestService;

    private OtpService otpService;

    @Autowired
    public PermoutRequestsItemJsonController(PermoutRequestService permoutRequestService, OtpService otpService) {
        this.permoutRequestService = permoutRequestService;
        this.otpService = otpService;
    }


    public PermoutRequestService getPermoutRequestService() {
        return permoutRequestService;
    }


    public void setPermoutRequestService(PermoutRequestService permoutRequestService) {
        this.permoutRequestService = permoutRequestService;
    }


    @ModelAttribute
    public PermoutRequest getPermoutRequest(@PathVariable("permoutRequest") Long id) {
        PermoutRequest permoutRequest = permoutRequestService.findOne(id);
        if (permoutRequest == null) {
            throw new NotFoundException(String.format("PermoutRequest with identifier '%s' not found", id));
        }
        return permoutRequest;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute PermoutRequest permoutRequest) {
        log.debug("show: {}", permoutRequest);
        return ResponseEntity.ok(permoutRequest);
    }


    public static UriComponents showURI(PermoutRequest permoutRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(PermoutRequestsItemJsonController.class).show(permoutRequest)).buildAndExpand(permoutRequest.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute PermoutRequest storedPermoutRequest, @Valid @RequestBody PermoutRequest permoutRequest, BindingResult result) {
        log.debug("update: {}", permoutRequest);
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", permoutRequest, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        permoutRequest.setId(storedPermoutRequest.getId());
        PermoutRequest updatedPermoutRequest = getPermoutRequestService().save(permoutRequest);

        if(updatedPermoutRequest.getStatus().equals(RequestStatus.TRANSIT)){
            String otp = otpService.generateOtp(updatedPermoutRequest.getId());
            log.debug("OTP generated for requestId: {}, otp: {}", updatedPermoutRequest.getId(), otp);
        }

        log.debug("update saved: {}", updatedPermoutRequest);
        return ResponseEntity.ok(updatedPermoutRequest);
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute PermoutRequest permoutRequest) {
        log.debug("delete: {}", permoutRequest);
        getPermoutRequestService().delete(permoutRequest);
        return ResponseEntity.ok().build();
    }
}
