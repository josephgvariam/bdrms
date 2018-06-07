package in.bigdash.rms.application.web.api.request.pickup;
import in.bigdash.rms.model.request.PickupRequest;

import in.bigdash.rms.service.api.PickupRequestService;
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


@RestController
@RequestMapping(value = "/api/pickuprequests/{pickupRequest}", name = "PickupRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class PickupRequestsItemJsonController {


    private PickupRequestService pickupRequestService;


    @Autowired
    public PickupRequestsItemJsonController(PickupRequestService pickupRequestService) {
        this.pickupRequestService = pickupRequestService;
    }


    public PickupRequestService getPickupRequestService() {
        return pickupRequestService;
    }


    public void setPickupRequestService(PickupRequestService pickupRequestService) {
        this.pickupRequestService = pickupRequestService;
    }


    @ModelAttribute
    public PickupRequest getPickupRequest(@PathVariable("pickupRequest") Long id) {
        PickupRequest pickupRequest = pickupRequestService.findOne(id);
        if (pickupRequest == null) {
            throw new NotFoundException(String.format("PickupRequest with identifier '%s' not found", id));
        }
        return pickupRequest;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute PickupRequest pickupRequest) {
        return ResponseEntity.ok(pickupRequest);
    }


    public static UriComponents showURI(PickupRequest pickupRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(PickupRequestsItemJsonController.class).show(pickupRequest)).buildAndExpand(pickupRequest.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute PickupRequest storedPickupRequest, @Valid @RequestBody PickupRequest pickupRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        pickupRequest.setId(storedPickupRequest.getId());
        getPickupRequestService().save(pickupRequest);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute PickupRequest pickupRequest) {
        getPickupRequestService().delete(pickupRequest);
        return ResponseEntity.ok().build();
    }
}
