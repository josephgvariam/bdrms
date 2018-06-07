package in.bigdash.rms.application.web.facility;
import in.bigdash.rms.model.Facility;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.service.api.FacilityService;
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


@Controller
@RequestMapping(value = "/facilities/{facility}", name = "FacilitiesItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class FacilitiesItemThymeleafController implements ConcurrencyManager<Facility> {


    private final ConcurrencyTemplate<Facility> concurrencyTemplate = new ConcurrencyTemplate<Facility>(this);


    private MethodLinkBuilderFactory<FacilitiesItemThymeleafController> itemLink;


    private FacilityService facilityService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<FacilitiesCollectionThymeleafController> collectionLink;


    @Autowired
    public FacilitiesItemThymeleafController(FacilityService facilityService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setFacilityService(facilityService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(FacilitiesItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(FacilitiesCollectionThymeleafController.class));
    }


    public FacilityService getFacilityService() {
        return facilityService;
    }


    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<FacilitiesItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<FacilitiesItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<FacilitiesCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<FacilitiesCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public Facility getFacility(@PathVariable("facility") Long id, Locale locale, HttpMethod method) {
        Facility facility = null;
        if (HttpMethod.PUT.equals(method)) {
            facility = facilityService.findOneForUpdate(id);
        } else {
            facility = facilityService.findOne(id);
        }
        if (facility == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "Facility", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return facility;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute Facility facility, Model model) {
        model.addAttribute("facility", facility);
        return new ModelAndView("facilities/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute Facility facility, Model model) {
        model.addAttribute("facility", facility);
        return new ModelAndView("facilities/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("createdDate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        model.addAttribute("modifiedDate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<Facility> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "facility";
    }


    public String getEditViewPath() {
        return "facilities/edit";
    }


    public Integer getLastVersion(Facility record) {
        Long versionValue = getFacilityService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(Facility entity, Model model) {
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


    @InitBinder("facility")
    public void initFacilityBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(Facility.class, getFacilityService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute Facility facility, Model model) {
        populateForm(model);
        model.addAttribute("facility", facility);
        return new ModelAndView("facilities/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute Facility facility, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        Facility savedFacility = getConcurrencyTemplate().execute(facility, model, new ConcurrencyCallback<Facility>() {

            @Override
            public Facility doInConcurrency(Facility facility) throws Exception {
                return getFacilityService().save(facility);
            }
        });
        UriComponents showURI = getItemLink().to(FacilitiesItemThymeleafLinkFactory.SHOW).with("facility", savedFacility.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Facility facility) {
        getFacilityService().delete(facility);
        return ResponseEntity.ok().build();
    }
}
