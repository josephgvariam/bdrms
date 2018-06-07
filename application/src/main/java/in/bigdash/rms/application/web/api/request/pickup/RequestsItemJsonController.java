package in.bigdash.rms.application.web.api.request.pickup;
import in.bigdash.rms.model.request.Request;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.RequestService;
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
 * = RequestsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Request.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/requests/{request}", name = "RequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestsItemJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RequestService requestService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param requestService
     */
    @Autowired
    public RequestsItemJsonController(RequestService requestService) {
        this.requestService = requestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RequestService
     */
    public RequestService getRequestService() {
        return requestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestService
     */
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Request
     */
    @ModelAttribute
    public Request getRequest(@PathVariable("request") Long id) {
        Request request = requestService.findOne(id);
        if (request == null) {
            throw new NotFoundException(String.format("Request with identifier '%s' not found", id));
        }
        return request;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Request request) {
        return ResponseEntity.ok(request);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @return UriComponents
     */
    public static UriComponents showURI(Request request) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RequestsItemJsonController.class).show(request)).buildAndExpand(request.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedRequest
     * @param request
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Request storedRequest, @Valid @RequestBody Request request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        request.setId(storedRequest.getId());
        getRequestService().save(request);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Request request) {
        getRequestService().delete(request);
        return ResponseEntity.ok().build();
    }
}
