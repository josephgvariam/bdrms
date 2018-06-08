package in.bigdash.rms.application.web.role;

import in.bigdash.rms.model.Role;
import in.bigdash.rms.service.api.RoleService;
import io.springlets.data.web.validation.GenericValidator;
import io.springlets.web.NotFoundException;
import io.springlets.web.mvc.util.ControllerMethodLinkBuilderFactory;
import io.springlets.web.mvc.util.MethodLinkBuilderFactory;
import io.springlets.web.mvc.util.concurrency.ConcurrencyCallback;
import io.springlets.web.mvc.util.concurrency.ConcurrencyManager;
import io.springlets.web.mvc.util.concurrency.ConcurrencyTemplate;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;
import java.util.Locale;


@Controller
@RequestMapping(value = "/roles/{role}", name = "RolesItemThymeleafController", produces = MediaType.TEXT_HTML_VALUE)
public class RolesItemThymeleafController implements ConcurrencyManager<Role> {


    private final ConcurrencyTemplate<Role> concurrencyTemplate = new ConcurrencyTemplate<Role>(this);


    private MethodLinkBuilderFactory<RolesItemThymeleafController> itemLink;


    private MessageSource messageSource;


    private MethodLinkBuilderFactory<RolesCollectionThymeleafController> collectionLink;


    private RoleService roleService;


    @Autowired
    public RolesItemThymeleafController(RoleService roleService, MessageSource messageSource, ControllerMethodLinkBuilderFactory linkBuilder) {
        setRoleService(roleService);
        setMessageSource(messageSource);
        setItemLink(linkBuilder.of(RolesItemThymeleafController.class));
        setCollectionLink(linkBuilder.of(RolesCollectionThymeleafController.class));
    }


    public RoleService getRoleService() {
        return roleService;
    }


    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    public MessageSource getMessageSource() {
        return messageSource;
    }


    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public MethodLinkBuilderFactory<RolesItemThymeleafController> getItemLink() {
        return itemLink;
    }


    public void setItemLink(MethodLinkBuilderFactory<RolesItemThymeleafController> itemLink) {
        this.itemLink = itemLink;
    }


    public MethodLinkBuilderFactory<RolesCollectionThymeleafController> getCollectionLink() {
        return collectionLink;
    }


    public void setCollectionLink(MethodLinkBuilderFactory<RolesCollectionThymeleafController> collectionLink) {
        this.collectionLink = collectionLink;
    }


    @ModelAttribute
    public Role getRole(@PathVariable("role") Long id, Locale locale, HttpMethod method) {
        Role role = null;
        if (HttpMethod.PUT.equals(method)) {
            role = roleService.findOneForUpdate(id);
        } else {
            role = roleService.findOne(id);
        }
        if (role == null) {
            String message = messageSource.getMessage("error_NotFound", new Object[] { "Role", id }, "The record couldn't be found", locale);
            throw new NotFoundException(message);
        }
        return role;
    }


    @GetMapping(name = "show")
    public ModelAndView show(@ModelAttribute Role role, Model model) {
        model.addAttribute("role", role);
        return new ModelAndView("roles/show");
    }


    @GetMapping(value = "/inline", name = "showInline")
    public ModelAndView showInline(@ModelAttribute Role role, Model model) {
        model.addAttribute("role", role);
        return new ModelAndView("roles/showInline :: inline-content");
    }


    public void populateFormats(Model model) {
        model.addAttribute("application_locale", LocaleContextHolder.getLocale().getLanguage());
    }


    public void populateForm(Model model) {
        populateFormats(model);
    }


    public ConcurrencyTemplate<Role> getConcurrencyTemplate() {
        return concurrencyTemplate;
    }


    public String getModelName() {
        return "role";
    }


    public String getEditViewPath() {
        return "roles/edit";
    }


    public Integer getLastVersion(Role record) {
        Long versionValue = getRoleService().findOne(record.getId()).getVersion();
        return versionValue != null ? versionValue.intValue() : null;
    }


    public ModelAndView populateAndGetFormView(Role entity, Model model) {
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
        return new ModelAndView(getEditViewPath(), model.asMap());
    }


    @InitBinder("role")
    public void initRoleBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
        // Register validators
        GenericValidator validator = new GenericValidator(Role.class, getRoleService());
        binder.addValidators(validator);
    }


    @GetMapping(value = "/edit-form", name = "editForm")
    public ModelAndView editForm(@ModelAttribute Role role, Model model) {
        populateForm(model);
        model.addAttribute("role", role);
        return new ModelAndView("roles/edit");
    }


    @PutMapping(name = "update")
    public ModelAndView update(@Valid @ModelAttribute Role role, BindingResult result, @RequestParam("version") Long version, @RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
        // Check if provided form contain errors
        if (result.hasErrors()) {
            populateForm(model);
            return new ModelAndView(getEditViewPath());
        }
        // Create Concurrency Spring Template to ensure that the following code will manage the
        // possible concurrency exceptions that appears and execute the provided coded inside the Spring template.
        // If some concurrency exception appears the template will manage it.
        Role savedRole = getConcurrencyTemplate().execute(role, model, new ConcurrencyCallback<Role>() {

            @Override
            public Role doInConcurrency(Role role) throws Exception {
                return getRoleService().save(role);
            }
        });
        UriComponents showURI = getItemLink().to(RolesItemThymeleafLinkFactory.SHOW).with("role", savedRole.getId()).toUri();
        return new ModelAndView("redirect:" + showURI.toUriString());
    }


    @ResponseBody
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Role role) {
        getRoleService().delete(role);
        return ResponseEntity.ok().build();
    }
}
