package in.bigdash.rms.repository;
import in.bigdash.rms.model.Role;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.User;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = RoleRepositoryCustom
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = Role.class)
public interface RoleRepositoryCustom {

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
}
