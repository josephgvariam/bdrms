package in.bigdash.rms.application.web.api.role;

import in.bigdash.rms.model.Role;
import in.bigdash.rms.service.api.RoleService;
import io.springlets.web.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;



@RestController
@RequestMapping(value = "/api/roles/{role}", name = "RolesItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RolesItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RoleService roleService;


    @Autowired
    public RolesItemJsonController(RoleService roleService) {
        this.roleService = roleService;
    }


    public RoleService getRoleService() {
        return roleService;
    }


    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @ModelAttribute
    public Role getRole(@PathVariable("role") Long id) {
        Role role = roleService.findOne(id);
        if (role == null) {
            throw new NotFoundException(String.format("Role with identifier '%s' not found", id));
        }
        return role;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Role role) {
        log.debug("show: {}", role);
        return ResponseEntity.ok(role);
    }


    public static UriComponents showURI(Role role) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RolesItemJsonController.class).show(role)).buildAndExpand(role.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Role storedRole, @Valid @RequestBody Role role, BindingResult result) {
        log.debug("update: {}", role);
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", role, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        role.setId(storedRole.getId());
        Role updatedRole = getRoleService().save(role);
        log.debug("update saved: {}", updatedRole);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Role role) {
        log.debug("delete: {}", role);
        getRoleService().delete(role);
        return ResponseEntity.ok().build();
    }
}
