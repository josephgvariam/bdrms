package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.Request;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = Request.class)
@Transactional(readOnly = true)
public interface RequestRepository extends DetachableJpaRepository<Request, Long>, RequestRepositoryCustom {


    public abstract long countByUserAssigned(User userAssigned);


    public abstract long countByStorageType(StorageType storageType);


    public abstract long countByUserCreated(User userCreated);
}
