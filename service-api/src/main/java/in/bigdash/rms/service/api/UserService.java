package in.bigdash.rms.service.api;
import in.bigdash.rms.model.User;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import in.bigdash.rms.model.Client;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService extends EntityResolver<User, Long>, ValidatorService<User> {


    public abstract User findOne(Long id);

    public abstract User findByUsername(String username);

    public abstract void delete(User user);


    public abstract List<User> save(Iterable<User> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract User save(User entity);


    public abstract User findOneForUpdate(Long id);


    public abstract List<User> findAll(Iterable<Long> ids);


    public abstract List<User> findAll();


    public abstract long count();


    public abstract Page<User> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<User> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract User addToRequestsAssigned(User user, Iterable<Long> requestsAssignedToAdd);


    public abstract User removeFromRequestsAssigned(User user, Iterable<Long> requestsAssignedToRemove);


    public abstract User setRequestsAssigned(User user, Iterable<Long> requestsAssigned);


    public abstract User addToRoles(User user, Iterable<Long> rolesToAdd);


    public abstract User removeFromRoles(User user, Iterable<Long> rolesToRemove);


    public abstract User setRoles(User user, Iterable<Long> roles);


    public abstract Page<User> findByClient(Client client, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByClient(Client client);
}
