package in.bigdash.rms.application.web.api.user;
import in.bigdash.rms.model.User;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.UserService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;


@RooController(entity = User.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/users/{user}", name = "UsersItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersItemJsonController {


    private UserService userService;


    @Autowired
    public UsersItemJsonController(UserService userService) {
        this.userService = userService;
    }


    public UserService getUserService() {
        return userService;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @ModelAttribute
    public User getUser(@PathVariable("user") Long id) {
        User user = userService.findOne(id);
        if (user == null) {
            throw new NotFoundException(String.format("User with identifier '%s' not found", id));
        }
        return user;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute User user) {
        return ResponseEntity.ok(user);
    }


    public static UriComponents showURI(User user) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(UsersItemJsonController.class).show(user)).buildAndExpand(user.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute User storedUser, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        user.setId(storedUser.getId());
        getUserService().save(user);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute User user) {
        getUserService().delete(user);
        return ResponseEntity.ok().build();
    }
}
