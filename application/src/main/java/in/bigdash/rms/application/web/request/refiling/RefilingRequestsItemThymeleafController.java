package in.bigdash.rms.application.web.request.refiling;
import in.bigdash.rms.model.request.RefilingRequest;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.service.api.RefilingRequestService;
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


@RooController(entity = RefilingRequest.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/refilingrequests/{refilingRequest}", name = "RefilingRequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class RefilingRequestsItemThymeleafController implements ConcurrencyManager<RefilingRequest> {


    private final ConcurrencyTemplate<RefilingRequest> concurrencyTemplate = new ConcurrencyTemplate<RefilingRequest>(this);


    private MethodLinkBuilderFactory<RefilingRequestsItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private RefilingRequestService refilingRequestService;


    private MethodLinkBuilderFactory<RefilingRequestsCollectionThymeleafController> collectionLink;


    @Autowired
    public RefilingRequestsItemThymeleafController(RefilingRequestService refilingRequestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setRefilingRequestService(refilingRequestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(RefilingRequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(RefilingRequestsCollectionThymeleafController.class));
    }


    public RefilingRequestService getRefilingRequestService() {
        return refilingRequestService;
    }


    public void setRefilingRequestService(RefilingRequestService refilingRequestService) {
        this.refilingRequestService = refilingRequestService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<RefilingRequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<RefilingRequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<RefilingRequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<RefilingRequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public RefilingRequest getRefilingRequest(@PathVariable("refilingRequest") Long id, Locale locale, HttpMethod method) {
        RefilingRequest refilingRequest = null;
        if (HttpMethod.PUT.equals(method)) {
            refilingRequest = refilingRequestService.findOneForUpdate(id);
        } else {
            refilingRequest = refilingRequestService.findOne(id);
        }
        if (refilingRequest == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "RefilingRequest", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return refilingRequest;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute RefilingRequest refilingRequest, Model model) {
        model.addAttribute("refilingRequest", refilingRequest);
        return new ModelAndView("refilingrequests/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute RefilingRequest refilingRequest, Model model) {
        model.addAttribute("refilingRequest", refilingRequest);
        return new ModelAndView("refilingrequests/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<RefilingRequest> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "refilingRequest";
    }


    public String getEditViewPath() {
        return "refilingrequests/edit";
    }


    public Integer getLastVersion(RefilingRequest record) {
        Long versionValue = getRefilingRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(RefilingRequest entity, Model model) {
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


    @InitBinder("refilingRequest")
    public void initRefilingRequestBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(RefilingRequest.class, getRefilingRequestService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute RefilingRequest refilingRequest, Model model) {
        populateForm(model);
        model.addAttribute("refilingRequest", refilingRequest);
        return new ModelAndView("refilingrequests/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute RefilingRequest refilingRequest, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        RefilingRequest savedRefilingRequest = getConcurrencyTemplate().execute(refilingRequest, model, new ConcurrencyCallback<RefilingRequest>() {

            @Override
            public RefilingRequest doInConcurrency(RefilingRequest refilingRequest) throws Exception {
                return getRefilingRequestService().save(refilingRequest);
            }
        });
        UriComponents showURI = getItemLink().to(RefilingRequestsItemThymeleafLinkFactory.SHOW).with("refilingRequest", savedRefilingRequest.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute RefilingRequest refilingRequest) {
        getRefilingRequestService().delete(refilingRequest);
        return ResponseEntity.ok().build();
    }
}
