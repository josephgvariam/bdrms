package in.bigdash.rms.repository;
import in.bigdash.rms.model.StorageType;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Client;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = StorageType.class)
@Transactional(readOnly = true)
public interface StorageTypeRepository extends DetachableJpaRepository<StorageType, Long>, StorageTypeRepositoryCustom {


    public abstract long countByClientsContains(Client clients);
}
