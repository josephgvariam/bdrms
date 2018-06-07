package in.bigdash.rms.application.web.user;
import in.bigdash.rms.model.User;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import in.bigdash.rms.service.api.UserService;
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


@RooController(entity = User.class, type = ControllerType.ITEM)
@RooThymeleaf
@Controller
@RequestMapping(value = "/users/{user}", name = "UsersItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class UsersItemThymeleafController implements ConcurrencyManager<User> {


    private final ConcurrencyTemplate<User> concurrencyTemplate = new ConcurrencyTemplate<User>(this);


    private MethodLinkBuilderFactory<UsersItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<UsersCollectionThymeleafController> collectionLink;


    private UserService userService;


    @Autowired
    public UsersItemThymeleafController(UserService userService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setUserService(userService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(UsersItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(UsersCollectionThymeleafController.class));
    }


    public UserService getUserService() {
        return userService;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<UsersItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<UsersItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<UsersCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<UsersCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public User getUser(@PathVariable("user") Long id, Locale locale, HttpMethod method) {
        User user = null;
        if (HttpMethod.PUT.equals(method)) {
            user = userService.findOneForUpdate(id);
        } else {
            user = userService.findOne(id);
        }
        if (user == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "User", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return user;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return new ModelAndView("users/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return new ModelAndView("users/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
        model.addAttribute("createdDate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        model.addAttribute("modifiedDate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<User> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "user";
    }


    public String getEditViewPath() {
        return "users/edit";
    }


    public Integer getLastVersion(User record) {
        Long versionValue = getUserService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(User entity, Model model) {
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


    @InitBinder("user")
    public void initUserBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(User.class, getUserService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute User user, Model model) {
        populateForm(model);
        model.addAttribute("user", user);
        return new ModelAndView("users/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute User user, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        User savedUser = getConcurrencyTemplate().execute(user, model, new ConcurrencyCallback<User>() {

            @Override
            public User doInConcurrency(User user) throws Exception {
                return getUserService().save(user);
            }
        });
        UriComponents showURI = getItemLink().to(UsersItemThymeleafLinkFactory.SHOW).with("user", savedUser.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute User user) {
        getUserService().delete(user);
        return ResponseEntity.ok().build();
    }
}
