package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.Request;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * = RequestRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Request.class)
@Transactional(readOnly = true)
public interface RequestRepository extends DetachableJpaRepository<Request, Long>, RequestRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param userAssigned
     * @return Long
     */
    public abstract long countByUserAssigned(User userAssigned);

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @return Long
     */
    public abstract long countByStorageType(StorageType storageType);

    /**
     * TODO Auto-generated method documentation
     *
     * @param userCreated
     * @return Long
     */
    public abstract long countByUserCreated(User userCreated);
}
