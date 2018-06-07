package in.bigdash.rms.application.web.request.permout;
import in.bigdash.rms.model.request.PermoutRequest;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.service.api.PermoutRequestService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;
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


@Controller
@RequestMapping(value = "/permoutrequests/{permoutRequest}", name = "PermoutRequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class PermoutRequestsItemThymeleafController implements ConcurrencyManager<PermoutRequest> {


    private PermoutRequestService permoutRequestService;


    private final ConcurrencyTemplate<PermoutRequest> concurrencyTemplate = new ConcurrencyTemplate<PermoutRequest>(this);


    private MethodLinkBuilderFactory<PermoutRequestsItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<PermoutRequestsCollectionThymeleafController> collectionLink;


    @Autowired
    public PermoutRequestsItemThymeleafController(PermoutRequestService permoutRequestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setPermoutRequestService(permoutRequestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(PermoutRequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(PermoutRequestsCollectionThymeleafController.class));
    }


    public PermoutRequestService getPermoutRequestService() {
        return permoutRequestService;
    }


    public void setPermoutRequestService(PermoutRequestService permoutRequestService) {
        this.permoutRequestService = permoutRequestService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<PermoutRequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<PermoutRequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<PermoutRequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<PermoutRequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public PermoutRequest getPermoutRequest(@PathVariable("permoutRequest") Long id, Locale locale, HttpMethod method) {
        PermoutRequest permoutRequest = null;
        if (HttpMethod.PUT.equals(method)) {
            permoutRequest = permoutRequestService.findOneForUpdate(id);
        } else {
            permoutRequest = permoutRequestService.findOne(id);
        }
        if (permoutRequest == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "PermoutRequest", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return permoutRequest;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute PermoutRequest permoutRequest, Model model) {
        model.addAttribute("permoutRequest", permoutRequest);
        return new ModelAndView("permoutrequests/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute PermoutRequest permoutRequest, Model model) {
        model.addAttribute("permoutRequest", permoutRequest);
        return new ModelAndView("permoutrequests/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<PermoutRequest> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "permoutRequest";
    }


    public String getEditViewPath() {
        return "permoutrequests/edit";
    }


    public Integer getLastVersion(PermoutRequest record) {
        Long versionValue = getPermoutRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(PermoutRequest entity, Model model) {
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


    @InitBinder("permoutRequest")
    public void initPermoutRequestBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(PermoutRequest.class, getPermoutRequestService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute PermoutRequest permoutRequest, Model model) {
        populateForm(model);
        model.addAttribute("permoutRequest", permoutRequest);
        return new ModelAndView("permoutrequests/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute PermoutRequest permoutRequest, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        PermoutRequest savedPermoutRequest = getConcurrencyTemplate().execute(permoutRequest, model, new ConcurrencyCallback<PermoutRequest>() {

            @Override
            public PermoutRequest doInConcurrency(PermoutRequest permoutRequest) throws Exception {
                return getPermoutRequestService().save(permoutRequest);
            }
        });
        UriComponents showURI = getItemLink().to(PermoutRequestsItemThymeleafLinkFactory.SHOW).with("permoutRequest", savedPermoutRequest.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute PermoutRequest permoutRequest) {
        getPermoutRequestService().delete(permoutRequest);
        return ResponseEntity.ok().build();
    }
}
