package in.bigdash.rms.repository.request;
import in.bigdash.rms.model.request.DestructionRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface DestructionRequestRepository extends DetachableJpaRepository<DestructionRequest, Long>, DestructionRequestRepositoryCustom {
}
