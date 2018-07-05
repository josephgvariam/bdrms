package in.bigdash.rms.application.web.api.request.refiling;
import in.bigdash.rms.model.request.RefilingRequest;

import in.bigdash.rms.service.api.RefilingRequestService;
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
@RequestMapping(value = "/api/refilingrequests/{refilingRequest}", name = "RefilingRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RefilingRequestsItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RefilingRequestService refilingRequestService;


    @Autowired
    public RefilingRequestsItemJsonController(RefilingRequestService refilingRequestService) {
        this.refilingRequestService = refilingRequestService;
    }


    public RefilingRequestService getRefilingRequestService() {
        return refilingRequestService;
    }


    public void setRefilingRequestService(RefilingRequestService refilingRequestService) {
        this.refilingRequestService = refilingRequestService;
    }


    @ModelAttribute
    public RefilingRequest getRefilingRequest(@PathVariable("refilingRequest") Long id) {
        RefilingRequest refilingRequest = refilingRequestService.findOne(id);
        if (refilingRequest == null) {
            throw new NotFoundException(String.format("RefilingRequest with identifier '%s' not found", id));
        }
        return refilingRequest;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute RefilingRequest refilingRequest) {
        log.debug("show: {}", refilingRequest);
        return ResponseEntity.ok(refilingRequest);
    }


    public static UriComponents showURI(RefilingRequest refilingRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RefilingRequestsItemJsonController.class).show(refilingRequest)).buildAndExpand(refilingRequest.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute RefilingRequest storedRefilingRequest, @Valid @RequestBody RefilingRequest refilingRequest, BindingResult result) {
        log.debug("update: {}", refilingRequest);
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", refilingRequest, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        refilingRequest.setId(storedRefilingRequest.getId());
        RefilingRequest updatedRefilingRequest = getRefilingRequestService().save(refilingRequest);
        log.debug("update saved: {}", updatedRefilingRequest);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute RefilingRequest refilingRequest) {
        log.debug("delete: {}", refilingRequest);
        getRefilingRequestService().delete(refilingRequest);
        return ResponseEntity.ok().build();
    }
}
