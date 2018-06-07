package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.PermoutRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface PermoutRequestRepository extends DetachableJpaRepository<PermoutRequest, Long>, PermoutRequestRepositoryCustom {
}
