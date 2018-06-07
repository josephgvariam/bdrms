package in.bigdash.rms.application.web.request.insertion;
import in.bigdash.rms.model.request.InsertionRequest;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.service.api.InsertionRequestService;
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
 * = InsertionRequestsItemThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = InsertionRequest.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/insertionrequests/{insertionRequest}", name = "InsertionRequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class InsertionRequestsItemThymeleafController implements ConcurrencyManager<InsertionRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private final ConcurrencyTemplate<InsertionRequest> concurrencyTemplate = new ConcurrencyTemplate<InsertionRequest>(this);

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<InsertionRequestsItemThymeleafController> itemLink;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private InsertionRequestService insertionRequestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MessageSource messageSource;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MethodLinkBuilderFactory<InsertionRequestsCollectionThymeleafController> collectionLink;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param insertionRequestService
     * @param messageSource
     * @param linkBuilder
     */
    @Autowired
    public InsertionRequestsItemThymeleafController(InsertionRequestService insertionRequestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setInsertionRequestService(insertionRequestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(InsertionRequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(InsertionRequestsCollectionThymeleafController.class));
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return InsertionRequestService
     */
    public InsertionRequestService getInsertionRequestService() {
        return insertionRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequestService
     */
    public void setInsertionRequestService(InsertionRequestService insertionRequestService) {
        this.insertionRequestService = insertionRequestService;
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
    public MethodLinkBuilderFactory<InsertionRequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param itemLink
     */
    public void setItemLink(MethodLinkBuilderFactory<InsertionRequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return MethodLinkBuilderFactory
     */
    public MethodLinkBuilderFactory<InsertionRequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param collectionLink
     */
    public void setCollectionLink(MethodLinkBuilderFactory<InsertionRequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @param locale
     * @param method
     * @return InsertionRequest
     */
    @ModelAttribute
    public InsertionRequest getInsertionRequest(@PathVariable("insertionRequest") Long id, Locale locale, HttpMethod method) {
        InsertionRequest insertionRequest = null;
        if (HttpMethod.PUT.equals(method)) {
            insertionRequest = insertionRequestService.findOneForUpdate(id);
        } else {
            insertionRequest = insertionRequestService.findOne(id);
        }
        if (insertionRequest == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "InsertionRequest", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return insertionRequest;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequest
     * @param model
     * @return ModelAndView
     */
    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute InsertionRequest insertionRequest, Model model) {
        model.addAttribute("insertionRequest", insertionRequest);
        return new ModelAndView("insertionrequests/show");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequest
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute InsertionRequest insertionRequest, Model model) {
        model.addAttribute("insertionRequest", insertionRequest);
        return new ModelAndView("insertionrequests/showInline :: inline-content");
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
    public ConcurrencyTemplate<InsertionRequest> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getModelName() {
        return "insertionRequest";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String getEditViewPath() {
        return "insertionrequests/edit";
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param record
     * @return Integer
     */
    public Integer getLastVersion(InsertionRequest record) {
        Long versionValue = getInsertionRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @param model
     * @return ModelAndView
     */
    public ModelAndView populateAndGetFormView(InsertionRequest entity, Model model) {
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
    @InitBinder("insertionRequest")
    public void initInsertionRequestBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(InsertionRequest.class, getInsertionRequestService());
        binder.addValidators(validator);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequest
     * @param model
     * @return ModelAndView
     */
    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute InsertionRequest insertionRequest, Model model) {
        populateForm(model);
        model.addAttribute("insertionRequest", insertionRequest);
        return new ModelAndView("insertionrequests/edit");
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequest
     * @param result
     * @param version
     * @param concurrencyControl
     * @param model
     * @return ModelAndView
     */
    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute InsertionRequest insertionRequest, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        InsertionRequest savedInsertionRequest = getConcurrencyTemplate().execute(insertionRequest, model, new ConcurrencyCallback<InsertionRequest>() {

            @Override
            public InsertionRequest doInConcurrency(InsertionRequest insertionRequest) throws Exception {
                return getInsertionRequestService().save(insertionRequest);
            }
        });
        UriComponents showURI = getItemLink().to(InsertionRequestsItemThymeleafLinkFactory.SHOW).with("insertionRequest", savedInsertionRequest.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequest
     * @return ResponseEntity
     */
    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute InsertionRequest insertionRequest) {
        getInsertionRequestService().delete(insertionRequest);
        return ResponseEntity.ok().build();
    }
}
