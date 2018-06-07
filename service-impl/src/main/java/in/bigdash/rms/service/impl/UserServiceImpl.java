package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.UserService;
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


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {


    private RequestService requestService;


    private UserRepository userRepository;


    private RoleService roleService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy RoleService roleService, @Lazy RequestService requestService) {
        setUserRepository(userRepository);
        setRoleService(roleService);
        setRequestService(requestService);
    }


    public UserRepository getUserRepository() {
        return userRepository;
    }


    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public RoleService getRoleService() {
        return roleService;
    }


    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    public RequestService getRequestService() {
        return requestService;
    }


    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }


    public Map<String, List<MessageI18n>> validate(User user) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public User addToRequestsAssigned(User user, Iterable<Long> requestsAssignedToAdd) {
        List<Request> requestsAssigned = getRequestService().findAll(requestsAssignedToAdd);
        user.addToRequestsAssigned(requestsAssigned);
        return getUserRepository().save(user);
    }


    @Transactional
    public User addToRoles(User user, Iterable<Long> rolesToAdd) {
        List<Role> roles = getRoleService().findAll(rolesToAdd);
        user.addToRoles(roles);
        return getUserRepository().save(user);
    }


    @Transactional
    public User removeFromRequestsAssigned(User user, Iterable<Long> requestsAssignedToRemove) {
        List<Request> requestsAssigned = getRequestService().findAll(requestsAssignedToRemove);
        user.removeFromRequestsAssigned(requestsAssigned);
        return getUserRepository().save(user);
    }


    @Transactional
    public User removeFromRoles(User user, Iterable<Long> rolesToRemove) {
        List<Role> roles = getRoleService().findAll(rolesToRemove);
        user.removeFromRoles(roles);
        return getUserRepository().save(user);
    }


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


    @Transactional
    public List<User> save(Iterable<User> entities) {
        return getUserRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<User> toDelete = getUserRepository().findAll(ids);
        getUserRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public User save(User entity) {
        return getUserRepository().save(entity);
    }

    public User findByUsername(String username){
        return getUserRepository().findByUsername(username);
    }

    public User findOne(Long id) {
        return getUserRepository().findOne(id);
    }


    public User findOneForUpdate(Long id) {
        return getUserRepository().findOneDetached(id);
    }


    public List<User> findAll(Iterable<Long> ids) {
        return getUserRepository().findAll(ids);
    }


    public List<User> findAll() {
        return getUserRepository().findAll();
    }


    public long count() {
        return getUserRepository().count();
    }


    public Page<User> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getUserRepository().findAll(globalSearch, pageable);
    }


    public Page<User> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getUserRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<User> findByClient(Client client, GlobalSearch globalSearch, Pageable pageable) {
        return getUserRepository().findByClient(client, globalSearch, pageable);
    }


    public long countByClient(Client client) {
        return getUserRepository().countByClient(client);
    }


    public Class<User> getEntityType() {
        return User.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
