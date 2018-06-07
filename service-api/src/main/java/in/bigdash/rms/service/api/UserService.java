package in.bigdash.rms.service.api;
import in.bigdash.rms.model.User;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.Client;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = UserService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = User.class)
public interface UserService extends EntityResolver<User, Long>, ValidatorService<User> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return User
     */
    public abstract User findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     */
    public abstract void delete(User user);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<User> save(Iterable<User> entities);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    public abstract void delete(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return User
     */
    public abstract User save(User entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return User
     */
    public abstract User findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<User> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<User> findAll();

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public abstract long count();

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<User> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<User> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param requestsAssignedToAdd
     * @return User
     */
    public abstract User addToRequestsAssigned(User user, Iterable<Long> requestsAssignedToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param requestsAssignedToRemove
     * @return User
     */
    public abstract User removeFromRequestsAssigned(User user, Iterable<Long> requestsAssignedToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param requestsAssigned
     * @return User
     */
    public abstract User setRequestsAssigned(User user, Iterable<Long> requestsAssigned);

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param rolesToAdd
     * @return User
     */
    public abstract User addToRoles(User user, Iterable<Long> rolesToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param rolesToRemove
     * @return User
     */
    public abstract User removeFromRoles(User user, Iterable<Long> rolesToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param user
     * @param roles
     * @return User
     */
    public abstract User setRoles(User user, Iterable<Long> roles);

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<User> findByClient(Client client, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @return Long
     */
    public abstract long countByClient(Client client);
}
