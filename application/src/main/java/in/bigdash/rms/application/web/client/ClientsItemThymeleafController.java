package in.bigdash.rms.application.web.client;
import in.bigdash.rms.model.Client;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.service.api.ClientService;
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


@RooController(entity = Client.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/clients/{client}", name = "ClientsItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class ClientsItemThymeleafController implements ConcurrencyManager<Client> {


    private final ConcurrencyTemplate<Client> concurrencyTemplate = new ConcurrencyTemplate<Client>(this);


    private MethodLinkBuilderFactory<ClientsItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private ClientService clientService;


    private MethodLinkBuilderFactory<ClientsCollectionThymeleafController> collectionLink;


    @Autowired
    public ClientsItemThymeleafController(ClientService clientService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setClientService(clientService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(ClientsItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(ClientsCollectionThymeleafController.class));
    }


    public ClientService getClientService() {
        return clientService;
    }


    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<ClientsItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<ClientsItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<ClientsCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<ClientsCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public Client getClient(@PathVariable("client") Long id, Locale locale, HttpMethod method) {
        Client client = null;
        if (HttpMethod.PUT.equals(method)) {
            client = clientService.findOneForUpdate(id);
        } else {
            client = clientService.findOne(id);
        }
        if (client == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "Client", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return client;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute Client client, Model model) {
        model.addAttribute("client", client);
        return new ModelAndView("clients/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute Client client, Model model) {
        model.addAttribute("client", client);
        return new ModelAndView("clients/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("createdDate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        model.addAttribute("modifiedDate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<Client> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "client";
    }


    public String getEditViewPath() {
        return "clients/edit";
    }


    public Integer getLastVersion(Client record) {
        Long versionValue = getClientService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(Client entity, Model model) {
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


    @InitBinder("client")
    public void initClientBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(Client.class, getClientService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute Client client, Model model) {
        populateForm(model);
        model.addAttribute("client", client);
        return new ModelAndView("clients/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute Client client, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        Client savedClient = getConcurrencyTemplate().execute(client, model, new ConcurrencyCallback<Client>() {

            @Override
            public Client doInConcurrency(Client client) throws Exception {
                return getClientService().save(client);
            }
        });
        UriComponents showURI = getItemLink().to(ClientsItemThymeleafLinkFactory.SHOW).with("client", savedClient.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Client client) {
        getClientService().delete(client);
        return ResponseEntity.ok().build();
    }
}
