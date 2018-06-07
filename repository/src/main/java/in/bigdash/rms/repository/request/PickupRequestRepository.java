package in.bigdash.rms.repository.request;
import in.bigdash.rms.model.request.PickupRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface PickupRequestRepository extends DetachableJpaRepository<PickupRequest, Long>, PickupRequestRepositoryCustom {
}
