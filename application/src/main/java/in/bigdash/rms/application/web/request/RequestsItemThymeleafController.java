package in.bigdash.rms.application.web.request;
import in.bigdash.rms.model.request.Request;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;

import in.bigdash.rms.model.request.RequestStatus;
import in.bigdash.rms.service.api.RequestService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;
import java.util.Arrays;
import java.util.Locale;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping(value = "/requests/{request}", name = "RequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class RequestsItemThymeleafController implements ConcurrencyManager<Request> {


    private final ConcurrencyTemplate<Request> concurrencyTemplate = new ConcurrencyTemplate<Request>(this);


    private MethodLinkBuilderFactory<RequestsItemThymeleafController> itemLink;


    private RequestService requestService;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<RequestsCollectionThymeleafController> collectionLink;


    @Autowired
    public RequestsItemThymeleafController(RequestService requestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setRequestService(requestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(RequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(RequestsCollectionThymeleafController.class));
    }


    public RequestService getRequestService() {
        return requestService;
    }


    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<RequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<RequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<RequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<RequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public Request getRequest(@PathVariable("request") Long id, Locale locale, HttpMethod method) {
        Request request = null;
        if (HttpMethod.PUT.equals(method)) {
            request = requestService.findOneForUpdate(id);
        } else {
            request = requestService.findOne(id);
        }
        if (request == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "Request", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return request;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute Request request, Model model) {
        return new ModelAndView("forward:/" + request.getType().toLowerCase() + "requests/" + request.getId());
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute Request request, Model model) {
        model.addAttribute("request", request);
        return new ModelAndView("requests/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("createdDate_date_format", "yyyy-MM-dd HH:mm:ss");
        model.addAttribute("modifiedDate_date_format", "yyyy-MM-dd HH:mm:ss");
    }


    public void populateForm(Model model) {
        populateFormats(model);
        model.addAttribute("status", Arrays.asList(RequestStatus.values()));
    }


    public ConcurrencyTemplate<Request> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "request";
    }


    public String getEditViewPath() {
        return "requests/edit";
    }


    public Integer getLastVersion(Request record) {
        Long versionValue = getRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(Request entity, Model model) {
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


    @InitBinder("request")
    public void initRequestBinder(WebDataBinder binder) {

    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute Request request, Model model) {
        return new ModelAndView("forward:/" + request.getType().toLowerCase() + "requests/" + request.getId() + "/edit-form");
    }

    @GetMapping(value = "/workflow/**", name = "workflow")
    public ModelAndView workflow(@ModelAttribute Request request, Model model) {
        model.addAttribute("request", request);
        return new ModelAndView("requests/workflow");
    }

    @GetMapping(value = "/loadchart", name = "loadchart")
    public ModelAndView loadchart(@ModelAttribute Request request, Model model) {
        if(request.getType().equals("PICKUP")) {
            return new ModelAndView("forward:/" + request.getType().toLowerCase() + "requests/" + request.getId() + "/loadchart");
        }

        return new ModelAndView("forward:/" + request.getType().toLowerCase() + "requests/" + request.getId());
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute Request request, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        Request savedRequest = getConcurrencyTemplate().execute(request, model, new ConcurrencyCallback<Request>() {

            @Override
            public Request doInConcurrency(Request request) throws Exception {
                return getRequestService().save(request);
            }
        });
        UriComponents showURI = getItemLink().to(RequestsItemThymeleafLinkFactory.SHOW).with("request", savedRequest.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Request request) {
        if(request.getStatus() != RequestStatus.OPEN){
            throw new RuntimeException("Cannot delete non-open requests");
        }
        getRequestService().delete(request);
        return ResponseEntity.ok().build();
    }
}
