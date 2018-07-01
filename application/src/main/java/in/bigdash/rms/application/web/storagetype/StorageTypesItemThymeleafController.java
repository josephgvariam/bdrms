package in.bigdash.rms.application.web.storagetype;

import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.service.api.StorageTypeService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;
import java.util.Locale;


@Controller
@RequestMapping(value = "/storagetypes/{storageType}", name = "StorageTypesItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class StorageTypesItemThymeleafController implements ConcurrencyManager<StorageType> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private StorageTypeService storageTypeService;


    private final ConcurrencyTemplate<StorageType> concurrencyTemplate = new ConcurrencyTemplate<StorageType>(this);


    private MethodLinkBuilderFactory<StorageTypesItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<StorageTypesCollectionThymeleafController> collectionLink;


    @Autowired
    public StorageTypesItemThymeleafController(StorageTypeService storageTypeService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setStorageTypeService(storageTypeService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(StorageTypesItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(StorageTypesCollectionThymeleafController.class));
    }


    public StorageTypeService getStorageTypeService() {
        return storageTypeService;
    }


    public void setStorageTypeService(StorageTypeService storageTypeService) {
        this.storageTypeService = storageTypeService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<StorageTypesItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<StorageTypesItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<StorageTypesCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<StorageTypesCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public StorageType getStorageType(@PathVariable("storageType") Long id, Locale locale, HttpMethod method) {
        StorageType storageType = null;
        if (HttpMethod.PUT.equals(method)) {
            storageType = storageTypeService.findOneForUpdate(id);
        } else {
            storageType = storageTypeService.findOne(id);
        }
        if (storageType == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "StorageType", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return storageType;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute StorageType storageType, Model model) {
        model.addAttribute("storageType", storageType);
        return new ModelAndView("storagetypes/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute StorageType storageType, Model model) {
        model.addAttribute("storageType", storageType);
        return new ModelAndView("storagetypes/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<StorageType> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "storageType";
    }


    public String getEditViewPath() {
        return "storagetypes/edit";
    }


    public Integer getLastVersion(StorageType record) {
        Long versionValue = getStorageTypeService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(StorageType entity, Model model) {
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
        return new ModelAndView(getEditViewPath(), model.asMap());
    }


    @InitBinder("storageType")
    public void initStorageTypeBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(StorageType.class, getStorageTypeService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute StorageType storageType, Model model) {
        populateForm(model);
        model.addAttribute("storageType", storageType);
        return new ModelAndView("storagetypes/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute StorageType storageType, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        StorageType savedStorageType = getConcurrencyTemplate().execute(storageType, model, new ConcurrencyCallback<StorageType>() {

            @Override
            public StorageType doInConcurrency(StorageType storageType) throws Exception {
                return getStorageTypeService().save(storageType);
            }
        });
        UriComponents showURI = getItemLink().to(StorageTypesItemThymeleafLinkFactory.SHOW).with("storageType", savedStorageType.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute StorageType storageType) {
        getStorageTypeService().delete(storageType);
        return ResponseEntity.ok().build();
    }
}
