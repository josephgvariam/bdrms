package in.bigdash.rms.application.web.api.document;
import in.bigdash.rms.model.Document;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.DocumentService;
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


@RooController(entity = Document.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/documents/{document}", name = "DocumentsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentsItemJsonController {


    private DocumentService documentService;


    @Autowired
    public DocumentsItemJsonController(DocumentService documentService) {
        this.documentService = documentService;
    }


    public DocumentService getDocumentService() {
        return documentService;
    }


    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }


    @ModelAttribute
    public Document getDocument(@PathVariable("document") Long id) {
        Document document = documentService.findOne(id);
        if (document == null) {
            throw new NotFoundException(String.format("Document with identifier '%s' not found", id));
        }
        return document;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Document document) {
        return ResponseEntity.ok(document);
    }


    public static UriComponents showURI(Document document) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(DocumentsItemJsonController.class).show(document)).buildAndExpand(document.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Document storedDocument, @Valid @RequestBody Document document, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        document.setId(storedDocument.getId());
        getDocumentService().save(document);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Document document) {
        getDocumentService().delete(document);
        return ResponseEntity.ok().build();
    }
}
