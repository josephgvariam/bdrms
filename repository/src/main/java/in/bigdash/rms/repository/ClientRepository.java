package in.bigdash.rms.repository;
import in.bigdash.rms.model.Client;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = Client.class)
@Transactional(readOnly = true)
public interface ClientRepository extends DetachableJpaRepository<Client, Long>, ClientRepositoryCustom {
}
