package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.model.inventory.InventoryItemStatus;
import in.bigdash.rms.service.api.DocumentInventoryItemService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;
import java.util.Arrays;
import java.util.Locale;
import javax.validation.Valid;
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


@RooController(entity = DocumentInventoryItem.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/documentinventoryitems/{documentInventoryItem}", name = "DocumentInventoryItemsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class DocumentInventoryItemsItemThymeleafController implements ConcurrencyManager<DocumentInventoryItem> {


    private final ConcurrencyTemplate<DocumentInventoryItem> concurrencyTemplate = new ConcurrencyTemplate<DocumentInventoryItem>(this);


    private MethodLinkBuilderFactory<DocumentInventoryItemsItemThymeleafController> itemLink;


    private DocumentInventoryItemService documentInventoryItemService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<DocumentInventoryItemsCollectionThymeleafController> collectionLink;


    @Autowired
    public DocumentInventoryItemsItemThymeleafController(DocumentInventoryItemService documentInventoryItemService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setDocumentInventoryItemService(documentInventoryItemService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(DocumentInventoryItemsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(DocumentInventoryItemsCollectionThymeleafController.class));
    }


    public DocumentInventoryItemService getDocumentInventoryItemService() {
        return documentInventoryItemService;
    }


    public void setDocumentInventoryItemService(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<DocumentInventoryItemsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<DocumentInventoryItemsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<DocumentInventoryItemsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<DocumentInventoryItemsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public DocumentInventoryItem getDocumentInventoryItem(@PathVariable("documentInventoryItem") Long id, Locale locale, HttpMethod method) {
        DocumentInventoryItem documentInventoryItem = null;
        if (HttpMethod.PUT.equals(method)) {
            documentInventoryItem = documentInventoryItemService.findOneForUpdate(id);
        } else {
            documentInventoryItem = documentInventoryItemService.findOne(id);
        }
        if (documentInventoryItem == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "DocumentInventoryItem", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return documentInventoryItem;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute DocumentInventoryItem documentInventoryItem, Model model) {
        model.addAttribute("documentInventoryItem", documentInventoryItem);
        return new ModelAndView("documentinventoryitems/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute DocumentInventoryItem documentInventoryItem, Model model) {
        model.addAttribute("documentInventoryItem", documentInventoryItem);
        return new ModelAndView("documentinventoryitems/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
        model.addAttribute("status", Arrays.asList(InventoryItemStatus.values()));
    }


    public ConcurrencyTemplate<DocumentInventoryItem> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "documentInventoryItem";
    }


    public String getEditViewPath() {
        return "documentinventoryitems/edit";
    }


    public Integer getLastVersion(DocumentInventoryItem record) {
        Long versionValue = getDocumentInventoryItemService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(DocumentInventoryItem entity, Model model) {
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


    @InitBinder("documentInventoryItem")
    public void initDocumentInventoryItemBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(DocumentInventoryItem.class, getDocumentInventoryItemService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute DocumentInventoryItem documentInventoryItem, Model model) {
        populateForm(model);
        model.addAttribute("documentInventoryItem", documentInventoryItem);
        return new ModelAndView("documentinventoryitems/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute DocumentInventoryItem documentInventoryItem, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        DocumentInventoryItem savedDocumentInventoryItem = getConcurrencyTemplate().execute(documentInventoryItem, model, new ConcurrencyCallback<DocumentInventoryItem>() {

            @Override
            public DocumentInventoryItem doInConcurrency(DocumentInventoryItem documentInventoryItem) throws Exception {
                return getDocumentInventoryItemService().save(documentInventoryItem);
            }
        });
        UriComponents showURI = getItemLink().to(DocumentInventoryItemsItemThymeleafLinkFactory.SHOW).with("documentInventoryItem", savedDocumentInventoryItem.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute DocumentInventoryItem documentInventoryItem) {
        getDocumentInventoryItemService().delete(documentInventoryItem);
        return ResponseEntity.ok().build();
    }
}
