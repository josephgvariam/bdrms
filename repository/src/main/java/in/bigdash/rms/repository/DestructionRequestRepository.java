package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.DestructionRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = DestructionRequest.class)
@Transactional(readOnly = true)
public interface DestructionRequestRepository extends DetachableJpaRepository<DestructionRequest, Long>, DestructionRequestRepositoryCustom {
}
