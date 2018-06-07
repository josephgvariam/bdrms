package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.RefilingRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface RefilingRequestRepository extends DetachableJpaRepository<RefilingRequest, Long>, RefilingRequestRepositoryCustom {
}
