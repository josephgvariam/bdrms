package in.bigdash.rms.repository;
import in.bigdash.rms.model.Role;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * = RoleRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Role.class)
@Transactional(readOnly = true)
public interface RoleRepository extends DetachableJpaRepository<Role, Long>, RoleRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param users
     * @return Long
     */
    public abstract long countByUsersContains(User users);
}
