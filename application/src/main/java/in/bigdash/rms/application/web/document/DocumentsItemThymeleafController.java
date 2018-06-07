package in.bigdash.rms.application.web.document;
import in.bigdash.rms.model.Document;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.service.api.DocumentService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;
import java.util.Locale;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;


@RooController(entity = Document.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/documents/{document}", name = "DocumentsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class DocumentsItemThymeleafController implements ConcurrencyManager<Document> {


    private final ConcurrencyTemplate<Document> concurrencyTemplate = new ConcurrencyTemplate<Document>(this);


    private MethodLinkBuilderFactory<DocumentsItemThymeleafController> itemLink;


    private DocumentService documentService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<DocumentsCollectionThymeleafController> collectionLink;


    @Autowired
    public DocumentsItemThymeleafController(DocumentService documentService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setDocumentService(documentService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(DocumentsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(DocumentsCollectionThymeleafController.class));
    }


    public DocumentService getDocumentService() {
        return documentService;
    }


    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<DocumentsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<DocumentsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<DocumentsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<DocumentsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public Document getDocument(@PathVariable("document") Long id, Locale locale, HttpMethod method) {
        Document document = null;
        if (HttpMethod.PUT.equals(method)) {
            document = documentService.findOneForUpdate(id);
        } else {
            document = documentService.findOne(id);
        }
        if (document == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "Document", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return document;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute Document document, Model model) {
        model.addAttribute("document", document);
        return new ModelAndView("documents/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute Document document, Model model) {
        model.addAttribute("document", document);
        return new ModelAndView("documents/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("createdDate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        model.addAttribute("modifiedDate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<Document> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "document";
    }


    public String getEditViewPath() {
        return "documents/edit";
    }


    public Integer getLastVersion(Document record) {
        Long versionValue = getDocumentService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(Document entity, Model model) {
        // Populate the form with all the necessary elements
        populateForm(model);
        // Add concurrency attribute to the model to show the concurrency form
        // in the current edit view
        model.addAttribute("concurrency", true);
        // Add the new version value to the model.
        model.addAttribute("newVersion", getLastVersion(entity));
        // Add the current pet values to maintain the values introduced by the user
        model.addAttribute(getModelName(), entity);
        // Return the edit view path
        return new org.springframework.web.servlet.ModelAndView(getEditViewPath(), model.asMap());
    }


    @InitBinder("document")
    public void initDocumentBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        binder.setDisallowedFields("inventoryItem.id");
        // Register validators
        GenericValidator validator = new GenericValidator(Document.class, getDocumentService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute Document document, Model model) {
        populateForm(model);
        model.addAttribute("document", document);
        return new ModelAndView("documents/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute Document document, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        Document savedDocument = getConcurrencyTemplate().execute(document, model, new ConcurrencyCallback<Document>() {

            @Override
            public Document doInConcurrency(Document document) throws Exception {
                return getDocumentService().save(document);
            }
        });
        UriComponents showURI = getItemLink().to(DocumentsItemThymeleafLinkFactory.SHOW).with("document", savedDocument.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Document document) {
        getDocumentService().delete(document);
        return ResponseEntity.ok().build();
    }
}
