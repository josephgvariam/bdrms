package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.model.inventory.InventoryItemStatus;
import in.bigdash.rms.service.api.FileInventoryItemService;
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
 * = FileInventoryItemsItemThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = FileInventoryItem.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/fileinventoryitems/{fileInventoryItem}", name = "FileInventoryItemsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class FileInventoryItemsItemThymeleafController implements ConcurrencyManager<FileInventoryItem> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private final ConcurrencyTemplate<FileInventoryItem> concurrencyTemplate = new ConcurrencyTemplate<FileInventoryItem>(this);

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private FileInventoryItemService fileInventoryItemService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<FileInventoryItemsItemThymeleafController> itemLink;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MessageSource messageSource;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<FileInventoryItemsCollectionThymeleafController> collectionLink;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param fileInventoryItemService
     * @param messageSource
     * @param linkBuilder
     */
    @Autowired
    public FileInventoryItemsItemThymeleafController(FileInventoryItemService fileInventoryItemService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setFileInventoryItemService(fileInventoryItemService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(FileInventoryItemsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(FileInventoryItemsCollectionThymeleafController.class));
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return FileInventoryItemService
     */
    public FileInventoryItemService getFileInventoryItemService() {
        return fileInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItemService
     */
    public void setFileInventoryItemService(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
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
    public MethodLinkBuilderFactory<FileInventoryItemsItemThymeleafController> getItemLink() {
        return itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param itemLink
     */
    public void setItemLink(MethodLinkBuilderFactory<FileInventoryItemsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return MethodLinkBuilderFactory
     */
    public MethodLinkBuilderFactory<FileInventoryItemsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param collectionLink
     */
    public void setCollectionLink(MethodLinkBuilderFactory<FileInventoryItemsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @param locale
     * @param method
     * @return FileInventoryItem
     */
    @ModelAttribute
    public FileInventoryItem getFileInventoryItem(@PathVariable("fileInventoryItem") Long id, Locale locale, HttpMethod method) {
        FileInventoryItem fileInventoryItem = null;
        if (HttpMethod.PUT.equals(method)) {
            fileInventoryItem = fileInventoryItemService.findOneForUpdate(id);
        } else {
            fileInventoryItem = fileInventoryItemService.findOne(id);
        }
        if (fileInventoryItem == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "FileInventoryItem", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return fileInventoryItem;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     * @param model
     * @return ModelAndView
     */
    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute FileInventoryItem fileInventoryItem, Model model) {
        model.addAttribute("fileInventoryItem", fileInventoryItem);
        return new ModelAndView("fileinventoryitems/show");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute FileInventoryItem fileInventoryItem, Model model) {
        model.addAttribute("fileInventoryItem", fileInventoryItem);
        return new ModelAndView("fileinventoryitems/showInline :: inline-content");
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
    public ConcurrencyTemplate<FileInventoryItem> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getModelName() {
        return "fileInventoryItem";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getEditViewPath() {
        return "fileinventoryitems/edit";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param record
     * @return Integer
     */
    public Integer getLastVersion(FileInventoryItem record) {
        Long versionValue = getFileInventoryItemService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @param model
     * @return ModelAndView
     */
    public ModelAndView populateAndGetFormView(FileInventoryItem entity, Model model) {
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
    @InitBinder("fileInventoryItem")
    public void initFileInventoryItemBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(FileInventoryItem.class, getFileInventoryItemService());
        binder.addValidators(validator);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute FileInventoryItem fileInventoryItem, Model model) {
        populateForm(model);
        model.addAttribute("fileInventoryItem", fileInventoryItem);
        return new ModelAndView("fileinventoryitems/edit");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     * @param result
     * @param version
     * @param concurrencyControl
     * @param model
     * @return ModelAndView
     */
    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute FileInventoryItem fileInventoryItem, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        FileInventoryItem savedFileInventoryItem = getConcurrencyTemplate().execute(fileInventoryItem, model, new ConcurrencyCallback<FileInventoryItem>() {

            @Override
            public FileInventoryItem doInConcurrency(FileInventoryItem fileInventoryItem) throws Exception {
                return getFileInventoryItemService().save(fileInventoryItem);
            }
        });
        UriComponents showURI = getItemLink().to(FileInventoryItemsItemThymeleafLinkFactory.SHOW).with("fileInventoryItem", savedFileInventoryItem.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     * @return ResponseEntity
     */
    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute FileInventoryItem fileInventoryItem) {
        getFileInventoryItemService().delete(fileInventoryItem);
        return ResponseEntity.ok().build();
    }
}
