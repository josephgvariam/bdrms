package in.bigdash.rms.repository;
import in.bigdash.rms.model.User;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Client;
import org.springframework.transaction.annotation.Transactional;

/**
 * = UserRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = User.class)
@Transactional(readOnly = true)
public interface UserRepository extends DetachableJpaRepository<User, Long>, UserRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @return Long
     */
    public abstract long countByClient(Client client);
}
