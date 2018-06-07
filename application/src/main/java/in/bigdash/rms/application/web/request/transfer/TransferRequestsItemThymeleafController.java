package in.bigdash.rms.application.web.request.transfer;
import in.bigdash.rms.model.request.TransferRequest;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.service.api.TransferRequestService;
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


@RooController(entity = TransferRequest.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/transferrequests/{transferRequest}", name = "TransferRequestsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class TransferRequestsItemThymeleafController implements ConcurrencyManager<TransferRequest> {


    private final ConcurrencyTemplate<TransferRequest> concurrencyTemplate = new ConcurrencyTemplate<TransferRequest>(this);


    private MethodLinkBuilderFactory<TransferRequestsItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<TransferRequestsCollectionThymeleafController> collectionLink;


    private TransferRequestService transferRequestService;


    @Autowired
    public TransferRequestsItemThymeleafController(TransferRequestService transferRequestService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setTransferRequestService(transferRequestService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(TransferRequestsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(TransferRequestsCollectionThymeleafController.class));
    }


    public TransferRequestService getTransferRequestService() {
        return transferRequestService;
    }


    public void setTransferRequestService(TransferRequestService transferRequestService) {
        this.transferRequestService = transferRequestService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<TransferRequestsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<TransferRequestsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<TransferRequestsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<TransferRequestsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public TransferRequest getTransferRequest(@PathVariable("transferRequest") Long id, Locale locale, HttpMethod method) {
        TransferRequest transferRequest = null;
        if (HttpMethod.PUT.equals(method)) {
            transferRequest = transferRequestService.findOneForUpdate(id);
        } else {
            transferRequest = transferRequestService.findOne(id);
        }
        if (transferRequest == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "TransferRequest", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return transferRequest;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute TransferRequest transferRequest, Model model) {
        model.addAttribute("transferRequest", transferRequest);
        return new ModelAndView("transferrequests/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute TransferRequest transferRequest, Model model) {
        model.addAttribute("transferRequest", transferRequest);
        return new ModelAndView("transferrequests/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<TransferRequest> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "transferRequest";
    }


    public String getEditViewPath() {
        return "transferrequests/edit";
    }


    public Integer getLastVersion(TransferRequest record) {
        Long versionValue = getTransferRequestService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(TransferRequest entity, Model model) {
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


    @InitBinder("transferRequest")
    public void initTransferRequestBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(TransferRequest.class, getTransferRequestService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute TransferRequest transferRequest, Model model) {
        populateForm(model);
        model.addAttribute("transferRequest", transferRequest);
        return new ModelAndView("transferrequests/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute TransferRequest transferRequest, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        TransferRequest savedTransferRequest = getConcurrencyTemplate().execute(transferRequest, model, new ConcurrencyCallback<TransferRequest>() {

            @Override
            public TransferRequest doInConcurrency(TransferRequest transferRequest) throws Exception {
                return getTransferRequestService().save(transferRequest);
            }
        });
        UriComponents showURI = getItemLink().to(TransferRequestsItemThymeleafLinkFactory.SHOW).with("transferRequest", savedTransferRequest.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute TransferRequest transferRequest) {
        getTransferRequestService().delete(transferRequest);
        return ResponseEntity.ok().build();
    }
}
