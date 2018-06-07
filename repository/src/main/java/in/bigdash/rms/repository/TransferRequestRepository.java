package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.TransferRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * = TransferRequestRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = TransferRequest.class)
@Transactional(readOnly = true)
public interface TransferRequestRepository extends DetachableJpaRepository<TransferRequest, Long>, TransferRequestRepositoryCustom {
}
