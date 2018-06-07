package in.bigdash.rms.repository.storagetype;
import in.bigdash.rms.model.StorageType;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.Client;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface StorageTypeRepository extends DetachableJpaRepository<StorageType, Long>, StorageTypeRepositoryCustom {


    public abstract long countByClientsContains(Client clients);
}
