package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.PermoutRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = PermoutRequest.class)
@Transactional(readOnly = true)
public interface PermoutRequestRepository extends DetachableJpaRepository<PermoutRequest, Long>, PermoutRequestRepositoryCustom {
}
