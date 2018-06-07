package in.bigdash.rms.application.web.api.request.permout;
import in.bigdash.rms.model.request.PermoutRequest;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.PermoutRequestService;
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
 * = PermoutRequestsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = PermoutRequest.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/permoutrequests/{permoutRequest}", name = "PermoutRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermoutRequestsItemJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private PermoutRequestService permoutRequestService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param permoutRequestService
     */
    @Autowired
    public PermoutRequestsItemJsonController(PermoutRequestService permoutRequestService) {
        this.permoutRequestService = permoutRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return PermoutRequestService
     */
    public PermoutRequestService getPermoutRequestService() {
        return permoutRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param permoutRequestService
     */
    public void setPermoutRequestService(PermoutRequestService permoutRequestService) {
        this.permoutRequestService = permoutRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return PermoutRequest
     */
    @ModelAttribute
    public PermoutRequest getPermoutRequest(@PathVariable("permoutRequest") Long id) {
        PermoutRequest permoutRequest = permoutRequestService.findOne(id);
        if (permoutRequest == null) {
            throw new NotFoundException(String.format("PermoutRequest with identifier '%s' not found", id));
        }
        return permoutRequest;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param permoutRequest
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute PermoutRequest permoutRequest) {
        return ResponseEntity.ok(permoutRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param permoutRequest
     * @return UriComponents
     */
    public static UriComponents showURI(PermoutRequest permoutRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(PermoutRequestsItemJsonController.class).show(permoutRequest)).buildAndExpand(permoutRequest.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedPermoutRequest
     * @param permoutRequest
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute PermoutRequest storedPermoutRequest, @Valid @RequestBody PermoutRequest permoutRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        permoutRequest.setId(storedPermoutRequest.getId());
        getPermoutRequestService().save(permoutRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param permoutRequest
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute PermoutRequest permoutRequest) {
        getPermoutRequestService().delete(permoutRequest);
        return ResponseEntity.ok().build();
    }
}
