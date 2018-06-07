package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.PickupRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = PickupRequest.class)
@Transactional(readOnly = true)
public interface PickupRequestRepository extends DetachableJpaRepository<PickupRequest, Long>, PickupRequestRepositoryCustom {
}
