package in.bigdash.rms.application.web.api.request.insertion;
import in.bigdash.rms.model.request.InsertionRequest;

import in.bigdash.rms.service.api.InsertionRequestService;
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
@RequestMapping(value = "/api/insertionrequests/{insertionRequest}", name = "InsertionRequestsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class InsertionRequestsItemJsonController {


    private InsertionRequestService insertionRequestService;


    @Autowired
    public InsertionRequestsItemJsonController(InsertionRequestService insertionRequestService) {
        this.insertionRequestService = insertionRequestService;
    }


    public InsertionRequestService getInsertionRequestService() {
        return insertionRequestService;
    }


    public void setInsertionRequestService(InsertionRequestService insertionRequestService) {
        this.insertionRequestService = insertionRequestService;
    }


    @ModelAttribute
    public InsertionRequest getInsertionRequest(@PathVariable("insertionRequest") Long id) {
        InsertionRequest insertionRequest = insertionRequestService.findOne(id);
        if (insertionRequest == null) {
            throw new NotFoundException(String.format("InsertionRequest with identifier '%s' not found", id));
        }
        return insertionRequest;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute InsertionRequest insertionRequest) {
        return ResponseEntity.ok(insertionRequest);
    }


    public static UriComponents showURI(InsertionRequest insertionRequest) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(InsertionRequestsItemJsonController.class).show(insertionRequest)).buildAndExpand(insertionRequest.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute InsertionRequest storedInsertionRequest, @Valid @RequestBody InsertionRequest insertionRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        insertionRequest.setId(storedInsertionRequest.getId());
        getInsertionRequestService().save(insertionRequest);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute InsertionRequest insertionRequest) {
        getInsertionRequestService().delete(insertionRequest);
        return ResponseEntity.ok().build();
    }
}
