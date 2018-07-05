package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.InventoryItem;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.model.inventory.InventoryItemStatus;
import in.bigdash.rms.service.api.InventoryItemService;
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
@RequestMapping(value = "/inventoryitems/{inventoryItem}", name = "InventoryItemsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class InventoryItemsItemThymeleafController implements ConcurrencyManager<InventoryItem> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConcurrencyTemplate<InventoryItem> concurrencyTemplate = new ConcurrencyTemplate<InventoryItem>(this);


    private MethodLinkBuilderFactory<InventoryItemsItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<InventoryItemsCollectionThymeleafController> collectionLink;


    private InventoryItemService inventoryItemService;


    @Autowired
    public InventoryItemsItemThymeleafController(InventoryItemService inventoryItemService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setInventoryItemService(inventoryItemService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(InventoryItemsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(InventoryItemsCollectionThymeleafController.class));
    }


    public InventoryItemService getInventoryItemService() {
        return inventoryItemService;
    }


    public void setInventoryItemService(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<InventoryItemsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<InventoryItemsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<InventoryItemsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<InventoryItemsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public InventoryItem getInventoryItem(@PathVariable("inventoryItem") Long id, Locale locale, HttpMethod method) {
        log.debug("{} {}", method, id);
        InventoryItem inventoryItem = null;
        if (HttpMethod.PUT.equals(method)) {
            inventoryItem = inventoryItemService.findOneForUpdate(id);
        } else {
            inventoryItem = inventoryItemService.findOne(id);
        }
        if (inventoryItem == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "InventoryItem", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return inventoryItem;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute InventoryItem inventoryItem, Model model) {
        log.debug("show: {}", inventoryItem);
        return new ModelAndView("forward:/" + inventoryItem.getType().toLowerCase() + "inventoryitems/" + inventoryItem.getId());
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute InventoryItem inventoryItem, Model model) {
        model.addAttribute("inventoryItem", inventoryItem);
        return new ModelAndView("inventoryitems/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
        model.addAttribute("status", Arrays.asList(InventoryItemStatus.values()));
    }


    public ConcurrencyTemplate<InventoryItem> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "inventoryItem";
    }


    public String getEditViewPath() {
        return "inventoryitems/edit";
    }


    public Integer getLastVersion(InventoryItem record) {
        Long versionValue = getInventoryItemService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(InventoryItem entity, Model model) {
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


    @InitBinder("inventoryItem")
    public void initInventoryItemBinder(WebDataBinder binder) {

    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute InventoryItem inventoryItem, Model model) {
        log.debug("get edit form");
        return new ModelAndView("forward:/" + inventoryItem.getType().toLowerCase() + "inventoryitems/" + inventoryItem.getId() + "/edit-form");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute InventoryItem inventoryItem, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        log.debug("update: {}", inventoryItem);
        // Check if provided form contain errors
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", inventoryItem, result.getAllErrors());
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        InventoryItem savedInventoryItem = getConcurrencyTemplate().execute(inventoryItem, model, new ConcurrencyCallback<InventoryItem>() {

            @Override
            public InventoryItem doInConcurrency(InventoryItem inventoryItem) throws Exception {
                InventoryItem updatedInventoryItem =  getInventoryItemService().save(inventoryItem);
                log.debug("update saved: {}", updatedInventoryItem);
                return updatedInventoryItem;
            }
        });
        UriComponents showURI = getItemLink().to(InventoryItemsItemThymeleafLinkFactory.SHOW).with("inventoryItem", savedInventoryItem.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute InventoryItem inventoryItem) {
        log.debug("delete: {}", inventoryItem);
        getInventoryItemService().delete(inventoryItem);
        return ResponseEntity.ok().build();
    }
}
