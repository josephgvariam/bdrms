package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.UserService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.Role;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.repository.UserRepository;
import in.bigdash.rms.service.api.RequestService;
import in.bigdash.rms.service.api.RoleService;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * = UserServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = UserService.class)
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RequestService requestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private UserRepository userRepository;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RoleService roleService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param userRepository
     * @param roleService
     * @param requestService
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy RoleService roleService, @Lazy RequestService requestService) {
        setUserRepository(userRepository);
        setRoleService(roleService);
        setRequestService(requestService);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UserRepository
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param userRepository
     */
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RoleService
     */
    public RoleService getRoleService() {
        return roleService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param roleService
     */
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RequestService
     */
    public RequestService getRequestService() {
        return requestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestService
     */
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(User user) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param requestsAssignedToAdd
     * @return User
     */
    @Transactional
    public User addToRequestsAssigned(User user, Iterable<Long> requestsAssignedToAdd) {
        List<Request> requestsAssigned = getRequestService().findAll(requestsAssignedToAdd);
        user.addToRequestsAssigned(requestsAssigned);
        return getUserRepository().save(user);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param rolesToAdd
     * @return User
     */
    @Transactional
    public User addToRoles(User user, Iterable<Long> rolesToAdd) {
        List<Role> roles = getRoleService().findAll(rolesToAdd);
        user.addToRoles(roles);
        return getUserRepository().save(user);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param requestsAssignedToRemove
     * @return User
     */
    @Transactional
    public User removeFromRequestsAssigned(User user, Iterable<Long> requestsAssignedToRemove) {
        List<Request> requestsAssigned = getRequestService().findAll(requestsAssignedToRemove);
        user.removeFromRequestsAssigned(requestsAssigned);
        return getUserRepository().save(user);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param rolesToRemove
     * @return User
     */
    @Transactional
    public User removeFromRoles(User user, Iterable<Long> rolesToRemove) {
        List<Role> roles = getRoleService().findAll(rolesToRemove);
        user.removeFromRoles(roles);
        return getUserRepository().save(user);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param requestsAssigned
     * @return User
     */
    @Transactional
    public User setRequestsAssigned(User user, Iterable<Long> requestsAssigned) {
        List<Request> items = getRequestService().findAll(requestsAssigned);
        Set<Request> currents = user.getRequestsAssigned();
        Set<Request> toRemove = new HashSet<Request>();
        for (Iterator<Request> iterator = currents.iterator(); iterator.hasNext(); ) {
            Request nextRequest = iterator.next();
            if (items.contains(nextRequest)) {
                items.remove(nextRequest);
            } else {
                toRemove.add(nextRequest);
            }
        }
        user.removeFromRequestsAssigned(toRemove);
        user.addToRequestsAssigned(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        user.setVersion(user.getVersion() + 1);
        return getUserRepository().save(user);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param roles
     * @return User
     */
    @Transactional
    public User setRoles(User user, Iterable<Long> roles) {
        List<Role> items = getRoleService().findAll(roles);
        Set<Role> currents = user.getRoles();
        Set<Role> toRemove = new HashSet<Role>();
        for (Iterator<Role> iterator = currents.iterator(); iterator.hasNext(); ) {
            Role nextRole = iterator.next();
            if (items.contains(nextRole)) {
                items.remove(nextRole);
            } else {
                toRemove.add(nextRole);
            }
        }
        user.removeFromRoles(toRemove);
        user.addToRoles(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        user.setVersion(user.getVersion() + 1);
        return getUserRepository().save(user);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     */
    @Transactional
    public void delete(User user) {
        // Clear bidirectional many-to-one child relationship with Client
        if (user.getClient() != null) {
            user.getClient().getUsers().remove(user);
        }
        // Clear bidirectional one-to-many parent relationship with Request
        for (Request item : user.getRequestsAssigned()) {
            item.setUserAssigned(null);
        }
        // Clear bidirectional one-to-many parent relationship with Request
        for (Request item : user.getRequestsCreated()) {
            item.setUserCreated(null);
        }
        // Clear bidirectional many-to-many parent relationship with Role
        for (Role item : user.getRoles()) {
            item.getUsers().remove(user);
        }
        getUserRepository().delete(user);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<User> save(Iterable<User> entities) {
        return getUserRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<User> toDelete = getUserRepository().findAll(ids);
        getUserRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return User
     */
    @Transactional
    public User save(User entity) {
        return getUserRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return User
     */
    public User findOne(Long id) {
        return getUserRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return User
     */
    public User findOneForUpdate(Long id) {
        return getUserRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<User> findAll(Iterable<Long> ids) {
        return getUserRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<User> findAll() {
        return getUserRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getUserRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<User> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getUserRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<User> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getUserRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<User> findByClient(Client client, GlobalSearch globalSearch, Pageable pageable) {
        return getUserRepository().findByClient(client, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @return Long
     */
    public long countByClient(Client client) {
        return getUserRepository().countByClient(client);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<User> getEntityType() {
        return User.class;
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
