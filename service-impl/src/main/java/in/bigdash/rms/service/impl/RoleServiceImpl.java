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

/**
 * = RoleServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = RoleService.class)
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RoleRepository roleRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param roleRepository
     */
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        setRoleRepository(roleRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RoleRepository
     */
    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param roleRepository
     */
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param role
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(Role role) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param role
     */
    @Transactional
    public void delete(Role role) {
        // Clear bidirectional many-to-many child relationship with User
        for (User item : role.getUsers()) {
            item.getRoles().remove(role);
        }
        getRoleRepository().delete(role);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<Role> save(Iterable<Role> entities) {
        return getRoleRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Role> toDelete = getRoleRepository().findAll(ids);
        getRoleRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return Role
     */
    @Transactional
    public Role save(Role entity) {
        return getRoleRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Role
     */
    public Role findOne(Long id) {
        return getRoleRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Role
     */
    public Role findOneForUpdate(Long id) {
        return getRoleRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<Role> findAll(Iterable<Long> ids) {
        return getRoleRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<Role> findAll() {
        return getRoleRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getRoleRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Role> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getRoleRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Role> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getRoleRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param users
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Role> findByUsersContains(User users, GlobalSearch globalSearch, Pageable pageable) {
        return getRoleRepository().findByUsersContains(users, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param users
     * @return Long
     */
    public long countByUsersContains(User users) {
        return getRoleRepository().countByUsersContains(users);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<Role> getEntityType() {
        return Role.class;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<Long> getIdType() {
        return Long.class;
    }
}
