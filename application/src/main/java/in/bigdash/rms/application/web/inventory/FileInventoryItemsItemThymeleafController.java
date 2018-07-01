package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@Controller
@RequestMapping(value = "/fileinventoryitems/{fileInventoryItem}", name = "FileInventoryItemsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class FileInventoryItemsItemThymeleafController implements ConcurrencyManager<FileInventoryItem> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConcurrencyTemplate<FileInventoryItem> concurrencyTemplate = new ConcurrencyTemplate<FileInventoryItem>(this);


    private FileInventoryItemService fileInventoryItemService;


    private MethodLinkBuilderFactory<FileInventoryItemsItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<FileInventoryItemsCollectionThymeleafController> collectionLink;


    @Autowired
    public FileInventoryItemsItemThymeleafController(FileInventoryItemService fileInventoryItemService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setFileInventoryItemService(fileInventoryItemService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(FileInventoryItemsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(FileInventoryItemsCollectionThymeleafController.class));
    }


    public FileInventoryItemService getFileInventoryItemService() {
        return fileInventoryItemService;
    }


    public void setFileInventoryItemService(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<FileInventoryItemsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<FileInventoryItemsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<FileInventoryItemsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<FileInventoryItemsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


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


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute FileInventoryItem fileInventoryItem, Model model) {
        model.addAttribute("fileInventoryItem", fileInventoryItem);
        return new ModelAndView("fileinventoryitems/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute FileInventoryItem fileInventoryItem, Model model) {
        model.addAttribute("fileInventoryItem", fileInventoryItem);
        return new ModelAndView("fileinventoryitems/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
        model.addAttribute("status", Arrays.asList(InventoryItemStatus.values()));
    }


    public ConcurrencyTemplate<FileInventoryItem> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "fileInventoryItem";
    }


    public String getEditViewPath() {
        return "fileinventoryitems/edit";
    }


    public Integer getLastVersion(FileInventoryItem record) {
        Long versionValue = getFileInventoryItemService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


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


    @InitBinder("fileInventoryItem")
    public void initFileInventoryItemBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(FileInventoryItem.class, getFileInventoryItemService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute FileInventoryItem fileInventoryItem, Model model) {
        populateForm(model);
        model.addAttribute("fileInventoryItem", fileInventoryItem);
        return new ModelAndView("fileinventoryitems/edit");
    }


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


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute FileInventoryItem fileInventoryItem) {
        getFileInventoryItemService().delete(fileInventoryItem);
        return ResponseEntity.ok().build();
    }
}
