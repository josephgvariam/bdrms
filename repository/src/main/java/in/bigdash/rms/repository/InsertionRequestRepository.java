package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.InsertionRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface InsertionRequestRepository extends DetachableJpaRepository<InsertionRequest, Long>, InsertionRequestRepositoryCustom {
}
