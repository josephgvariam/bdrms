package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Role;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.User;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = RoleService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Role.class)
public interface RoleService extends EntityResolver<Role, Long>, ValidatorService<Role> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Role
     */
    public abstract Role findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param role
     */
    public abstract void delete(Role role);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<Role> save(Iterable<Role> entities);

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
     * @return Role
     */
    public abstract Role save(Role entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Role
     */
    public abstract Role findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<Role> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<Role> findAll();

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
    public abstract Page<Role> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Role> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param users
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Role> findByUsersContains(User users, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param users
     * @return Long
     */
    public abstract long countByUsersContains(User users);
}
