package in.bigdash.rms.application.web.shelf;
import in.bigdash.rms.model.Shelf;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.model.ShelfStatus;
import in.bigdash.rms.service.api.ShelfService;
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
@RequestMapping(value = "/shelves/{shelf}", name = "ShelvesItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class ShelvesItemThymeleafController implements ConcurrencyManager<Shelf> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConcurrencyTemplate<Shelf> concurrencyTemplate = new ConcurrencyTemplate<Shelf>(this);


    private MethodLinkBuilderFactory<ShelvesItemThymeleafController> itemLink;


    private ShelfService shelfService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<ShelvesCollectionThymeleafController> collectionLink;


    @Autowired
    public ShelvesItemThymeleafController(ShelfService shelfService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setShelfService(shelfService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(ShelvesItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(ShelvesCollectionThymeleafController.class));
    }


    public ShelfService getShelfService() {
        return shelfService;
    }


    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<ShelvesItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<ShelvesItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<ShelvesCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<ShelvesCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public Shelf getShelf(@PathVariable("shelf") Long id, Locale locale, HttpMethod method) {
        Shelf shelf = null;
        if (HttpMethod.PUT.equals(method)) {
            shelf = shelfService.findOneForUpdate(id);
        } else {
            shelf = shelfService.findOne(id);
        }
        if (shelf == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "Shelf", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return shelf;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute Shelf shelf, Model model) {
        model.addAttribute("shelf", shelf);
        return new ModelAndView("shelves/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute Shelf shelf, Model model) {
        model.addAttribute("shelf", shelf);
        return new ModelAndView("shelves/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
        model.addAttribute("status", Arrays.asList(ShelfStatus.values()));
    }


    public ConcurrencyTemplate<Shelf> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "shelf";
    }


    public String getEditViewPath() {
        return "shelves/edit";
    }


    public Integer getLastVersion(Shelf record) {
        Long versionValue = getShelfService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(Shelf entity, Model model) {
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


    @InitBinder("shelf")
    public void initShelfBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(Shelf.class, getShelfService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute Shelf shelf, Model model) {
        populateForm(model);
        model.addAttribute("shelf", shelf);
        return new ModelAndView("shelves/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute Shelf shelf, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        Shelf savedShelf = getConcurrencyTemplate().execute(shelf, model, new ConcurrencyCallback<Shelf>() {

            @Override
            public Shelf doInConcurrency(Shelf shelf) throws Exception {
                return getShelfService().save(shelf);
            }
        });
        UriComponents showURI = getItemLink().to(ShelvesItemThymeleafLinkFactory.SHOW).with("shelf", savedShelf.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Shelf shelf) {
        getShelfService().delete(shelf);
        return ResponseEntity.ok().build();
    }
}
