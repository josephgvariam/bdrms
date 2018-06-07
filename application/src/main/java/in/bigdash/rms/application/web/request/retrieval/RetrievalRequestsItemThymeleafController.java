package in.bigdash.rms.application.web.request.retrieval;
import in.bigdash.rms.model.request.RetrievalRequest;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.service.api.RetrievalRequestService;
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
 * = RetrievalRequestsItemThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = RetrievalRequest.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/retrievalrequests/{retrievalRequest}", name = "RetrievalRequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class RetrievalRequestsItemThymeleafController implements ConcurrencyManager<RetrievalRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private final ConcurrencyTemplate<RetrievalRequest> concurrencyTemplate = new ConcurrencyTemplate<RetrievalRequest>(this);

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<RetrievalRequestsItemThymeleafController> itemLink;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MessageSource messageSource;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<RetrievalRequestsCollectionThymeleafController> collectionLink;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RetrievalRequestService retrievalRequestService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param retrievalRequestService
     * @param messageSource
     * @param linkBuilder
     */
    @Autowired
    public RetrievalRequestsItemThymeleafController(RetrievalRequestService retrievalRequestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setRetrievalRequestService(retrievalRequestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(RetrievalRequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(RetrievalRequestsCollectionThymeleafController.class));
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RetrievalRequestService
     */
    public RetrievalRequestService getRetrievalRequestService() {
        return retrievalRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequestService
     */
    public void setRetrievalRequestService(RetrievalRequestService retrievalRequestService) {
        this.retrievalRequestService = retrievalRequestService;
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
    public MethodLinkBuilderFactory<RetrievalRequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param itemLink
     */
    public void setItemLink(MethodLinkBuilderFactory<RetrievalRequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return MethodLinkBuilderFactory
     */
    public MethodLinkBuilderFactory<RetrievalRequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param collectionLink
     */
    public void setCollectionLink(MethodLinkBuilderFactory<RetrievalRequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @param locale
     * @param method
     * @return RetrievalRequest
     */
    @ModelAttribute
    public RetrievalRequest getRetrievalRequest(@PathVariable("retrievalRequest") Long id, Locale locale, HttpMethod method) {
        RetrievalRequest retrievalRequest = null;
        if (HttpMethod.PUT.equals(method)) {
            retrievalRequest = retrievalRequestService.findOneForUpdate(id);
        } else {
            retrievalRequest = retrievalRequestService.findOne(id);
        }
        if (retrievalRequest == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "RetrievalRequest", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return retrievalRequest;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequest
     * @param model
     * @return ModelAndView
     */
    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute RetrievalRequest retrievalRequest, Model model) {
        model.addAttribute("retrievalRequest", retrievalRequest);
        return new ModelAndView("retrievalrequests/show");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequest
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute RetrievalRequest retrievalRequest, Model model) {
        model.addAttribute("retrievalRequest", retrievalRequest);
        return new ModelAndView("retrievalrequests/showInline :: inline-content");
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
    public ConcurrencyTemplate<RetrievalRequest> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getModelName() {
        return "retrievalRequest";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getEditViewPath() {
        return "retrievalrequests/edit";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param record
     * @return Integer
     */
    public Integer getLastVersion(RetrievalRequest record) {
        Long versionValue = getRetrievalRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @param model
     * @return ModelAndView
     */
    public ModelAndView populateAndGetFormView(RetrievalRequest entity, Model model) {
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
    @InitBinder("retrievalRequest")
    public void initRetrievalRequestBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(RetrievalRequest.class, getRetrievalRequestService());
        binder.addValidators(validator);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequest
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute RetrievalRequest retrievalRequest, Model model) {
        populateForm(model);
        model.addAttribute("retrievalRequest", retrievalRequest);
        return new ModelAndView("retrievalrequests/edit");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequest
     * @param result
     * @param version
     * @param concurrencyControl
     * @param model
     * @return ModelAndView
     */
    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute RetrievalRequest retrievalRequest, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        RetrievalRequest savedRetrievalRequest = getConcurrencyTemplate().execute(retrievalRequest, model, new ConcurrencyCallback<RetrievalRequest>() {

            @Override
            public RetrievalRequest doInConcurrency(RetrievalRequest retrievalRequest) throws Exception {
                return getRetrievalRequestService().save(retrievalRequest);
            }
        });
        UriComponents showURI = getItemLink().to(RetrievalRequestsItemThymeleafLinkFactory.SHOW).with("retrievalRequest", savedRetrievalRequest.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequest
     * @return ResponseEntity
     */
    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute RetrievalRequest retrievalRequest) {
        getRetrievalRequestService().delete(retrievalRequest);
        return ResponseEntity.ok().build();
    }
}
