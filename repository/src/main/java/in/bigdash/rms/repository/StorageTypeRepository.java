package in.bigdash.rms.repository;
import in.bigdash.rms.model.StorageType;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Client;
import org.springframework.transaction.annotation.Transactional;

/**
 * = StorageTypeRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = StorageType.class)
@Transactional(readOnly = true)
public interface StorageTypeRepository extends DetachableJpaRepository<StorageType, Long>, StorageTypeRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param clients
     * @return Long
     */
    public abstract long countByClientsContains(Client clients);
}
