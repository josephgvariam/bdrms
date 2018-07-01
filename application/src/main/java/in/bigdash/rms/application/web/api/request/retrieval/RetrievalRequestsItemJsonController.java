package in.bigdash.rms.application.web.api.request.retrieval;
import in.bigdash.rms.model.request.RetrievalRequest;

import in.bigdash.rms.service.api.RetrievalRequestService;
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
@RequestMapping(value = "/api/retrievalrequests/{retrievalRequest}", name = "RetrievalRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RetrievalRequestsItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RetrievalRequestService retrievalRequestService;


    @Autowired
    public RetrievalRequestsItemJsonController(RetrievalRequestService retrievalRequestService) {
        this.retrievalRequestService = retrievalRequestService;
    }


    public RetrievalRequestService getRetrievalRequestService() {
        return retrievalRequestService;
    }


    public void setRetrievalRequestService(RetrievalRequestService retrievalRequestService) {
        this.retrievalRequestService = retrievalRequestService;
    }


    @ModelAttribute
    public RetrievalRequest getRetrievalRequest(@PathVariable("retrievalRequest") Long id) {
        RetrievalRequest retrievalRequest = retrievalRequestService.findOne(id);
        if (retrievalRequest == null) {
            throw new NotFoundException(String.format("RetrievalRequest with identifier '%s' not found", id));
        }
        return retrievalRequest;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute RetrievalRequest retrievalRequest) {
        return ResponseEntity.ok(retrievalRequest);
    }


    public static UriComponents showURI(RetrievalRequest retrievalRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RetrievalRequestsItemJsonController.class).show(retrievalRequest)).buildAndExpand(retrievalRequest.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute RetrievalRequest storedRetrievalRequest, @Valid @RequestBody RetrievalRequest retrievalRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        retrievalRequest.setId(storedRetrievalRequest.getId());
        getRetrievalRequestService().save(retrievalRequest);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute RetrievalRequest retrievalRequest) {
        getRetrievalRequestService().delete(retrievalRequest);
        return ResponseEntity.ok().build();
    }
}
