package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Role;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import in.bigdash.rms.model.User;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RoleService extends EntityResolver<Role, Long>, ValidatorService<Role> {


    public abstract Role findOne(Long id);


    public abstract void delete(Role role);


    public abstract List<Role> save(Iterable<Role> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract Role save(Role entity);


    public abstract Role findOneForUpdate(Long id);


    public abstract List<Role> findAll(Iterable<Long> ids);


    public abstract List<Role> findAll();


    public abstract long count();


    public abstract Page<Role> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Role> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Role> findByUsersContains(User users, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByUsersContains(User users);
}
