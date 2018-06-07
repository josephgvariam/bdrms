package in.bigdash.rms.application.web.request.destruction;
import in.bigdash.rms.model.request.DestructionRequest;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.service.api.DestructionRequestService;
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

/**
 * = DestructionRequestsItemThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = DestructionRequest.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/destructionrequests/{destructionRequest}", name = "DestructionRequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class DestructionRequestsItemThymeleafController implements ConcurrencyManager<DestructionRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DestructionRequestService destructionRequestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private final ConcurrencyTemplate<DestructionRequest> concurrencyTemplate = new ConcurrencyTemplate<DestructionRequest>(this);

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<DestructionRequestsItemThymeleafController> itemLink;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MessageSource messageSource;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<DestructionRequestsCollectionThymeleafController> collectionLink;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param destructionRequestService
     * @param messageSource
     * @param linkBuilder
     */
    @Autowired
    public DestructionRequestsItemThymeleafController(DestructionRequestService destructionRequestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setDestructionRequestService(destructionRequestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(DestructionRequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(DestructionRequestsCollectionThymeleafController.class));
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DestructionRequestService
     */
    public DestructionRequestService getDestructionRequestService() {
        return destructionRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequestService
     */
    public void setDestructionRequestService(DestructionRequestService destructionRequestService) {
        this.destructionRequestService = destructionRequestService;
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
    public MethodLinkBuilderFactory<DestructionRequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param itemLink
     */
    public void setItemLink(MethodLinkBuilderFactory<DestructionRequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return MethodLinkBuilderFactory
     */
    public MethodLinkBuilderFactory<DestructionRequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param collectionLink
     */
    public void setCollectionLink(MethodLinkBuilderFactory<DestructionRequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @param locale
     * @param method
     * @return DestructionRequest
     */
    @ModelAttribute
    public DestructionRequest getDestructionRequest(@PathVariable("destructionRequest") Long id, Locale locale, HttpMethod method) {
        DestructionRequest destructionRequest = null;
        if (HttpMethod.PUT.equals(method)) {
            destructionRequest = destructionRequestService.findOneForUpdate(id);
        } else {
            destructionRequest = destructionRequestService.findOne(id);
        }
        if (destructionRequest == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "DestructionRequest", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return destructionRequest;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     * @param model
     * @return ModelAndView
     */
    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute DestructionRequest destructionRequest, Model model) {
        model.addAttribute("destructionRequest", destructionRequest);
        return new ModelAndView("destructionrequests/show");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute DestructionRequest destructionRequest, Model model) {
        model.addAttribute("destructionRequest", destructionRequest);
        return new ModelAndView("destructionrequests/showInline :: inline-content");
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
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ConcurrencyTemplate
     */
    public ConcurrencyTemplate<DestructionRequest> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getModelName() {
        return "destructionRequest";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getEditViewPath() {
        return "destructionrequests/edit";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param record
     * @return Integer
     */
    public Integer getLastVersion(DestructionRequest record) {
        Long versionValue = getDestructionRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @param model
     * @return ModelAndView
     */
    public ModelAndView populateAndGetFormView(DestructionRequest entity, Model model) {
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
    @InitBinder("destructionRequest")
    public void initDestructionRequestBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(DestructionRequest.class, getDestructionRequestService());
        binder.addValidators(validator);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute DestructionRequest destructionRequest, Model model) {
        populateForm(model);
        model.addAttribute("destructionRequest", destructionRequest);
        return new ModelAndView("destructionrequests/edit");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     * @param result
     * @param version
     * @param concurrencyControl
     * @param model
     * @return ModelAndView
     */
    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute DestructionRequest destructionRequest, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        DestructionRequest savedDestructionRequest = getConcurrencyTemplate().execute(destructionRequest, model, new ConcurrencyCallback<DestructionRequest>() {

            @Override
            public DestructionRequest doInConcurrency(DestructionRequest destructionRequest) throws Exception {
                return getDestructionRequestService().save(destructionRequest);
            }
        });
        UriComponents showURI = getItemLink().to(DestructionRequestsItemThymeleafLinkFactory.SHOW).with("destructionRequest", savedDestructionRequest.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     * @return ResponseEntity
     */
    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute DestructionRequest destructionRequest) {
        getDestructionRequestService().delete(destructionRequest);
        return ResponseEntity.ok().build();
    }
}
