package in.bigdash.rms.application.web.request.retrieval;
import in.bigdash.rms.model.request.RetrievalRequest;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.service.api.RetrievalRequestService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;
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
@RequestMapping(value = "/retrievalrequests/{retrievalRequest}", name = "RetrievalRequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class RetrievalRequestsItemThymeleafController implements ConcurrencyManager<RetrievalRequest> {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final ConcurrencyTemplate<RetrievalRequest> concurrencyTemplate = new ConcurrencyTemplate<RetrievalRequest>(this);


    private MethodLinkBuilderFactory<RetrievalRequestsItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<RetrievalRequestsCollectionThymeleafController> collectionLink;


    private RetrievalRequestService retrievalRequestService;


    @Autowired
    public RetrievalRequestsItemThymeleafController(RetrievalRequestService retrievalRequestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setRetrievalRequestService(retrievalRequestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(RetrievalRequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(RetrievalRequestsCollectionThymeleafController.class));
    }


    public RetrievalRequestService getRetrievalRequestService() {
        return retrievalRequestService;
    }


    public void setRetrievalRequestService(RetrievalRequestService retrievalRequestService) {
        this.retrievalRequestService = retrievalRequestService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<RetrievalRequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<RetrievalRequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<RetrievalRequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<RetrievalRequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


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


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute RetrievalRequest retrievalRequest, Model model) {
        model.addAttribute("retrievalRequest", retrievalRequest);
        return new ModelAndView("retrievalrequests/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute RetrievalRequest retrievalRequest, Model model) {
        model.addAttribute("retrievalRequest", retrievalRequest);
        return new ModelAndView("retrievalrequests/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<RetrievalRequest> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "retrievalRequest";
    }


    public String getEditViewPath() {
        return "retrievalrequests/edit";
    }


    public Integer getLastVersion(RetrievalRequest record) {
        Long versionValue = getRetrievalRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


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


    @InitBinder("retrievalRequest")
    public void initRetrievalRequestBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(RetrievalRequest.class, getRetrievalRequestService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute RetrievalRequest retrievalRequest, Model model) {
        populateForm(model);
        model.addAttribute("retrievalRequest", retrievalRequest);
        return new ModelAndView("retrievalrequests/edit");
    }


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


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute RetrievalRequest retrievalRequest) {
        getRetrievalRequestService().delete(retrievalRequest);
        return ResponseEntity.ok().build();
    }
}
