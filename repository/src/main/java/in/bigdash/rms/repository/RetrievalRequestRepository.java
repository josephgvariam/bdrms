package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.RetrievalRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface RetrievalRequestRepository extends DetachableJpaRepository<RetrievalRequest, Long>, RetrievalRequestRepositoryCustom {
}
