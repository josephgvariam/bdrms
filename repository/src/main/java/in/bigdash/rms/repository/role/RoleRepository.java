package in.bigdash.rms.repository.role;
import in.bigdash.rms.model.Role;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.User;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface RoleRepository extends DetachableJpaRepository<Role, Long>, RoleRepositoryCustom {


    public abstract long countByUsersContains(User users);
}
