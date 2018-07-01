package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.model.inventory.InventoryItemStatus;
import in.bigdash.rms.service.api.BoxInventoryItemService;
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
@RequestMapping(value = "/boxinventoryitems/{boxInventoryItem}", name = "BoxInventoryItemsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class BoxInventoryItemsItemThymeleafController implements ConcurrencyManager<BoxInventoryItem> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConcurrencyTemplate<BoxInventoryItem> concurrencyTemplate = new ConcurrencyTemplate<BoxInventoryItem>(this);


    private MethodLinkBuilderFactory<BoxInventoryItemsItemThymeleafController> itemLink;


    private BoxInventoryItemService boxInventoryItemService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<BoxInventoryItemsCollectionThymeleafController> collectionLink;


    @Autowired
    public BoxInventoryItemsItemThymeleafController(BoxInventoryItemService boxInventoryItemService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setBoxInventoryItemService(boxInventoryItemService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(BoxInventoryItemsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(BoxInventoryItemsCollectionThymeleafController.class));
    }


    public BoxInventoryItemService getBoxInventoryItemService() {
        return boxInventoryItemService;
    }


    public void setBoxInventoryItemService(BoxInventoryItemService boxInventoryItemService) {
        this.boxInventoryItemService = boxInventoryItemService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<BoxInventoryItemsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<BoxInventoryItemsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<BoxInventoryItemsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<BoxInventoryItemsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public BoxInventoryItem getBoxInventoryItem(@PathVariable("boxInventoryItem") Long id, Locale locale, HttpMethod method) {
        BoxInventoryItem boxInventoryItem = null;
        if (HttpMethod.PUT.equals(method)) {
            boxInventoryItem = boxInventoryItemService.findOneForUpdate(id);
        } else {
            boxInventoryItem = boxInventoryItemService.findOne(id);
        }
        if (boxInventoryItem == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "BoxInventoryItem", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return boxInventoryItem;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute BoxInventoryItem boxInventoryItem, Model model) {
        model.addAttribute("boxInventoryItem", boxInventoryItem);
        return new ModelAndView("boxinventoryitems/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute BoxInventoryItem boxInventoryItem, Model model) {
        model.addAttribute("boxInventoryItem", boxInventoryItem);
        return new ModelAndView("boxinventoryitems/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
        model.addAttribute("status", Arrays.asList(InventoryItemStatus.values()));
    }


    public ConcurrencyTemplate<BoxInventoryItem> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "boxInventoryItem";
    }


    public String getEditViewPath() {
        return "boxinventoryitems/edit";
    }


    public Integer getLastVersion(BoxInventoryItem record) {
        Long versionValue = getBoxInventoryItemService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(BoxInventoryItem entity, Model model) {
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


    @InitBinder("boxInventoryItem")
    public void initBoxInventoryItemBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(BoxInventoryItem.class, getBoxInventoryItemService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute BoxInventoryItem boxInventoryItem, Model model) {
        populateForm(model);
        model.addAttribute("boxInventoryItem", boxInventoryItem);
        return new ModelAndView("boxinventoryitems/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute BoxInventoryItem boxInventoryItem, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        BoxInventoryItem savedBoxInventoryItem = getConcurrencyTemplate().execute(boxInventoryItem, model, new ConcurrencyCallback<BoxInventoryItem>() {

            @Override
            public BoxInventoryItem doInConcurrency(BoxInventoryItem boxInventoryItem) throws Exception {
                return getBoxInventoryItemService().save(boxInventoryItem);
            }
        });
        UriComponents showURI = getItemLink().to(BoxInventoryItemsItemThymeleafLinkFactory.SHOW).with("boxInventoryItem", savedBoxInventoryItem.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute BoxInventoryItem boxInventoryItem) {
        getBoxInventoryItemService().delete(boxInventoryItem);
        return ResponseEntity.ok().build();
    }
}
