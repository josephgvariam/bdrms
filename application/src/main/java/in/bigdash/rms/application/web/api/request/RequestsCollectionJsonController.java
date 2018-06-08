package in.bigdash.rms.application.web.api.request;
import in.bigdash.rms.model.request.Request;

import in.bigdash.rms.service.api.RequestService;
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


@RestController
@RequestMapping(value = "/api/requests", name = "RequestsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestsCollectionJsonController {


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
        Page<Request> requests = getRequestService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(requests);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RequestsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Request request, BindingResult result) {
        if (request.getId() != null || request.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Request newRequest = getRequestService().save(request);
        UriComponents showURI = RequestsItemJsonController.showURI(newRequest);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Request> requests, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getRequestService().save(requests);
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Request> requests, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getRequestService().save(requests);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        getRequestService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
