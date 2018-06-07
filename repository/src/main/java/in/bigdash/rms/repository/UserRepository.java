package in.bigdash.rms.repository;
import in.bigdash.rms.model.User;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Client;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = User.class)
@Transactional(readOnly = true)
public interface UserRepository extends DetachableJpaRepository<User, Long>, UserRepositoryCustom {


    public abstract long countByClient(Client client);
}
