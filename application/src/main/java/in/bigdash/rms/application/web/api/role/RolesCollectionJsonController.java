package in.bigdash.rms.application.web.api.role;

import in.bigdash.rms.model.Role;
import in.bigdash.rms.service.api.RoleService;
import io.springlets.data.domain.GlobalSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequestMapping(value = "/api/roles", name = "RolesCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class RolesCollectionJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RoleService roleService;


    @Autowired
    public RolesCollectionJsonController(RoleService roleService) {
        this.roleService = roleService;
    }


    public RoleService getRoleService() {
        return roleService;
    }


    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<Role>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<Role> roles = getRoleService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(roles);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(RolesCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Role role, BindingResult result) {
        if (role.getId() != null || role.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Role newRole = getRoleService().save(role);
        UriComponents showURI = RolesItemJsonController.showURI(newRole);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Role> roles, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getRoleService().save(roles);
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Role> roles, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getRoleService().save(roles);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        getRoleService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
