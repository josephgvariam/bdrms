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

/**
 * = UsersItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = User.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/users/{user}", name = "UsersItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersItemJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private UserService userService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param userService
     */
    @Autowired
    public UsersItemJsonController(UserService userService) {
        this.userService = userService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UserService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param userService
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return User
     */
    @ModelAttribute
    public User getUser(@PathVariable("user") Long id) {
        User user = userService.findOne(id);
        if (user == null) {
            throw new NotFoundException(String.format("User with identifier '%s' not found", id));
        }
        return user;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute User user) {
        return ResponseEntity.ok(user);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @return UriComponents
     */
    public static UriComponents showURI(User user) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(UsersItemJsonController.class).show(user)).buildAndExpand(user.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedUser
     * @param user
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute User storedUser, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        user.setId(storedUser.getId());
        getUserService().save(user);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute User user) {
        getUserService().delete(user);
        return ResponseEntity.ok().build();
    }
}
