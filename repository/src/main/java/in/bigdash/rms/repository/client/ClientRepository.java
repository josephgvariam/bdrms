package in.bigdash.rms.repository.client;
import in.bigdash.rms.model.Client;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface ClientRepository extends DetachableJpaRepository<Client, Long>, ClientRepositoryCustom {
}
