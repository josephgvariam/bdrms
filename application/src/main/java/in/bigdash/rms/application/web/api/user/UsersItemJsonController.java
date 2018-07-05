package in.bigdash.rms.application.web.api.user;
import in.bigdash.rms.model.User;

import in.bigdash.rms.service.api.UserService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@RestController
@RequestMapping(value = "/api/users/{user}", name = "UsersItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
        log.debug("show: {}", user);
        return ResponseEntity.ok(user);
    }


    public static UriComponents showURI(User user) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(UsersItemJsonController.class).show(user)).buildAndExpand(user.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute User storedUser, @Valid @RequestBody User user, BindingResult result) {
        log.debug("update: {}", user);
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", user, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        user.setId(storedUser.getId());
        User updatedUser = getUserService().save(user);
        log.debug("update saved: {}", updatedUser);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute User user) {
        log.debug("delete: {}", user);
        getUserService().delete(user);
        return ResponseEntity.ok().build();
    }
}
