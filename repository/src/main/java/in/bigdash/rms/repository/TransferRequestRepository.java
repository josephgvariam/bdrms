package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.TransferRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface TransferRequestRepository extends DetachableJpaRepository<TransferRequest, Long>, TransferRequestRepositoryCustom {
}
