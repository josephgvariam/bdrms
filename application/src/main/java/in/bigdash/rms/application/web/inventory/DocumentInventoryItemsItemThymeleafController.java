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

/**
 * = DocumentInventoryItemsItemThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = DocumentInventoryItem.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/documentinventoryitems/{documentInventoryItem}", name = "DocumentInventoryItemsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class DocumentInventoryItemsItemThymeleafController implements ConcurrencyManager<DocumentInventoryItem> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private final ConcurrencyTemplate<DocumentInventoryItem> concurrencyTemplate = new ConcurrencyTemplate<DocumentInventoryItem>(this);

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<DocumentInventoryItemsItemThymeleafController> itemLink;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DocumentInventoryItemService documentInventoryItemService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MessageSource messageSource;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<DocumentInventoryItemsCollectionThymeleafController> collectionLink;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param documentInventoryItemService
     * @param messageSource
     * @param linkBuilder
     */
    @Autowired
    public DocumentInventoryItemsItemThymeleafController(DocumentInventoryItemService documentInventoryItemService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setDocumentInventoryItemService(documentInventoryItemService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(DocumentInventoryItemsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(DocumentInventoryItemsCollectionThymeleafController.class));
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DocumentInventoryItemService
     */
    public DocumentInventoryItemService getDocumentInventoryItemService() {
        return documentInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItemService
     */
    public void setDocumentInventoryItemService(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return MessageSource
     */
    public MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param messageSource
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return MethodLinkBuilderFactory
     */
    public MethodLinkBuilderFactory<DocumentInventoryItemsItemThymeleafController> getItemLink() {
        return itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param itemLink
     */
    public void setItemLink(MethodLinkBuilderFactory<DocumentInventoryItemsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return MethodLinkBuilderFactory
     */
    public MethodLinkBuilderFactory<DocumentInventoryItemsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param collectionLink
     */
    public void setCollectionLink(MethodLinkBuilderFactory<DocumentInventoryItemsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @param locale
     * @param method
     * @return DocumentInventoryItem
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     * @param model
     * @return ModelAndView
     */
    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute DocumentInventoryItem documentInventoryItem, Model model) {
        model.addAttribute("documentInventoryItem", documentInventoryItem);
        return new ModelAndView("documentinventoryitems/show");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute DocumentInventoryItem documentInventoryItem, Model model) {
        model.addAttribute("documentInventoryItem", documentInventoryItem);
        return new ModelAndView("documentinventoryitems/showInline :: inline-content");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param model
     */
    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param model
     */
    public void populateForm(Model model) {
        populateFormats(model);
        model.addAttribute("status", Arrays.asList(InventoryItemStatus.values()));
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ConcurrencyTemplate
     */
    public ConcurrencyTemplate<DocumentInventoryItem> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getModelName() {
        return "documentInventoryItem";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getEditViewPath() {
        return "documentinventoryitems/edit";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param record
     * @return Integer
     */
    public Integer getLastVersion(DocumentInventoryItem record) {
        Long versionValue = getDocumentInventoryItemService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @param model
     * @return ModelAndView
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param binder
     */
    @InitBinder("documentInventoryItem")
    public void initDocumentInventoryItemBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(DocumentInventoryItem.class, getDocumentInventoryItemService());
        binder.addValidators(validator);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute DocumentInventoryItem documentInventoryItem, Model model) {
        populateForm(model);
        model.addAttribute("documentInventoryItem", documentInventoryItem);
        return new ModelAndView("documentinventoryitems/edit");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     * @param result
     * @param version
     * @param concurrencyControl
     * @param model
     * @return ModelAndView
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     * @return ResponseEntity
     */
    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute DocumentInventoryItem documentInventoryItem) {
        getDocumentInventoryItemService().delete(documentInventoryItem);
        return ResponseEntity.ok().build();
    }
}
