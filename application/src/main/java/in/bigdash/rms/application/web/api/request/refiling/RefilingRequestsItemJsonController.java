package in.bigdash.rms.application.web.api.request.refiling;
import in.bigdash.rms.model.request.RefilingRequest;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.RefilingRequestService;
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
 * = RefilingRequestsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = RefilingRequest.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/refilingrequests/{refilingRequest}", name = "RefilingRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RefilingRequestsItemJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RefilingRequestService refilingRequestService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param refilingRequestService
     */
    @Autowired
    public RefilingRequestsItemJsonController(RefilingRequestService refilingRequestService) {
        this.refilingRequestService = refilingRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RefilingRequestService
     */
    public RefilingRequestService getRefilingRequestService() {
        return refilingRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param refilingRequestService
     */
    public void setRefilingRequestService(RefilingRequestService refilingRequestService) {
        this.refilingRequestService = refilingRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return RefilingRequest
     */
    @ModelAttribute
    public RefilingRequest getRefilingRequest(@PathVariable("refilingRequest") Long id) {
        RefilingRequest refilingRequest = refilingRequestService.findOne(id);
        if (refilingRequest == null) {
            throw new NotFoundException(String.format("RefilingRequest with identifier '%s' not found", id));
        }
        return refilingRequest;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param refilingRequest
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute RefilingRequest refilingRequest) {
        return ResponseEntity.ok(refilingRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param refilingRequest
     * @return UriComponents
     */
    public static UriComponents showURI(RefilingRequest refilingRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RefilingRequestsItemJsonController.class).show(refilingRequest)).buildAndExpand(refilingRequest.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedRefilingRequest
     * @param refilingRequest
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute RefilingRequest storedRefilingRequest, @Valid @RequestBody RefilingRequest refilingRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        refilingRequest.setId(storedRefilingRequest.getId());
        getRefilingRequestService().save(refilingRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param refilingRequest
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute RefilingRequest refilingRequest) {
        getRefilingRequestService().delete(refilingRequest);
        return ResponseEntity.ok().build();
    }
}
