package in.bigdash.rms.application.web.box;
import in.bigdash.rms.model.Box;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.service.api.BoxService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;
import java.util.Locale;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
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
@RequestMapping(value = "/boxes/{box}", name = "BoxesItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class BoxesItemThymeleafController implements ConcurrencyManager<Box> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConcurrencyTemplate<Box> concurrencyTemplate = new ConcurrencyTemplate<Box>(this);


    private MethodLinkBuilderFactory<BoxesItemThymeleafController> itemLink;


    private BoxService boxService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<BoxesCollectionThymeleafController> collectionLink;


    @Autowired
    public BoxesItemThymeleafController(BoxService boxService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setBoxService(boxService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(BoxesItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(BoxesCollectionThymeleafController.class));
    }


    public BoxService getBoxService() {
        return boxService;
    }


    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<BoxesItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<BoxesItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<BoxesCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<BoxesCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public Box getBox(@PathVariable("box") Long id, Locale locale, HttpMethod method) {
        Box box = null;
        if (HttpMethod.PUT.equals(method)) {
            box = boxService.findOneForUpdate(id);
        } else {
            box = boxService.findOne(id);
        }
        if (box == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "Box", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return box;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute Box box, Model model) {
        model.addAttribute("box", box);
        return new ModelAndView("boxes/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute Box box, Model model) {
        model.addAttribute("box", box);
        return new ModelAndView("boxes/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("createdDate_date_format", "yyyy-MM-dd HH:mm:ss");
        model.addAttribute("modifiedDate_date_format", "yyyy-MM-dd HH:mm:ss");
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<Box> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "box";
    }


    public String getEditViewPath() {
        return "boxes/edit";
    }


    public Integer getLastVersion(Box record) {
        Long versionValue = getBoxService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(Box entity, Model model) {
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


    @InitBinder("box")
    public void initBoxBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        binder.setDisallowedFields("inventoryItem.id");
        // Register validators
        GenericValidator validator = new GenericValidator(Box.class, getBoxService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute Box box, Model model) {
        populateForm(model);
        model.addAttribute("box", box);
        return new ModelAndView("boxes/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute Box box, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        Box savedBox = getConcurrencyTemplate().execute(box, model, new ConcurrencyCallback<Box>() {

            @Override
            public Box doInConcurrency(Box box) throws Exception {
                return getBoxService().save(box);
            }
        });
        UriComponents showURI = getItemLink().to(BoxesItemThymeleafLinkFactory.SHOW).with("box", savedBox.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Box box) {
        getBoxService().delete(box);
        return ResponseEntity.ok().build();
    }
}
