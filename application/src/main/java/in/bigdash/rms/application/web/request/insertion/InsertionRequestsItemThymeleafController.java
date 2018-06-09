package in.bigdash.rms.application.web.request.insertion;
import in.bigdash.rms.model.request.InsertionRequest;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.service.api.InsertionRequestService;
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
@RequestMapping(value = "/insertionrequests/{insertionRequest}", name = "InsertionRequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class InsertionRequestsItemThymeleafController implements ConcurrencyManager<InsertionRequest> {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final ConcurrencyTemplate<InsertionRequest> concurrencyTemplate = new ConcurrencyTemplate<InsertionRequest>(this);


    private MethodLinkBuilderFactory<InsertionRequestsItemThymeleafController> itemLink;


    private InsertionRequestService insertionRequestService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<InsertionRequestsCollectionThymeleafController> collectionLink;


    @Autowired
    public InsertionRequestsItemThymeleafController(InsertionRequestService insertionRequestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setInsertionRequestService(insertionRequestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(InsertionRequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(InsertionRequestsCollectionThymeleafController.class));
    }


    public InsertionRequestService getInsertionRequestService() {
        return insertionRequestService;
    }


    public void setInsertionRequestService(InsertionRequestService insertionRequestService) {
        this.insertionRequestService = insertionRequestService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<InsertionRequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<InsertionRequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<InsertionRequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<InsertionRequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


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


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute InsertionRequest insertionRequest, Model model) {
        model.addAttribute("insertionRequest", insertionRequest);
        return new ModelAndView("insertionrequests/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute InsertionRequest insertionRequest, Model model) {
        model.addAttribute("insertionRequest", insertionRequest);
        return new ModelAndView("insertionrequests/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<InsertionRequest> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "insertionRequest";
    }


    public String getEditViewPath() {
        return "insertionrequests/edit";
    }


    public Integer getLastVersion(InsertionRequest record) {
        Long versionValue = getInsertionRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


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


    @InitBinder("insertionRequest")
    public void initInsertionRequestBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(InsertionRequest.class, getInsertionRequestService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute InsertionRequest insertionRequest, Model model) {
        populateForm(model);
        model.addAttribute("insertionRequest", insertionRequest);
        return new ModelAndView("insertionrequests/edit");
    }


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


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute InsertionRequest insertionRequest) {
        getInsertionRequestService().delete(insertionRequest);
        return ResponseEntity.ok().build();
    }
}
