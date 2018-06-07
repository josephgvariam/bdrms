package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.RoleService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.Role;
import in.bigdash.rms.model.User;
import in.bigdash.rms.repository.RoleRepository;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RooServiceImpl(service = RoleService.class)
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {


    private RoleRepository roleRepository;


    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        setRoleRepository(roleRepository);
    }


    public RoleRepository getRoleRepository() {
        return roleRepository;
    }


    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Map<String, List<MessageI18n>> validate(Role role) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(Role role) {
        // Clear bidirectional many-to-many child relationship with User
        for (User item : role.getUsers()) {
            item.getRoles().remove(role);
        }
        getRoleRepository().delete(role);
    }


    @Transactional
    public List<Role> save(Iterable<Role> entities) {
        return getRoleRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Role> toDelete = getRoleRepository().findAll(ids);
        getRoleRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public Role save(Role entity) {
        return getRoleRepository().save(entity);
    }


    public Role findOne(Long id) {
        return getRoleRepository().findOne(id);
    }


    public Role findOneForUpdate(Long id) {
        return getRoleRepository().findOneDetached(id);
    }


    public List<Role> findAll(Iterable<Long> ids) {
        return getRoleRepository().findAll(ids);
    }


    public List<Role> findAll() {
        return getRoleRepository().findAll();
    }


    public long count() {
        return getRoleRepository().count();
    }


    public Page<Role> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getRoleRepository().findAll(globalSearch, pageable);
    }


    public Page<Role> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getRoleRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<Role> findByUsersContains(User users, GlobalSearch globalSearch, Pageable pageable) {
        return getRoleRepository().findByUsersContains(users, globalSearch, pageable);
    }


    public long countByUsersContains(User users) {
        return getRoleRepository().countByUsersContains(users);
    }


    public Class<Role> getEntityType() {
        return Role.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
