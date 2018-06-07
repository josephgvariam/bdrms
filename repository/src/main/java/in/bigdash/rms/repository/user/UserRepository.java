package in.bigdash.rms.repository.user;
import in.bigdash.rms.model.User;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.Client;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface UserRepository extends DetachableJpaRepository<User, Long>, UserRepositoryCustom {


    public abstract long countByClient(Client client);

    public abstract User findByUsername(String username);
}
