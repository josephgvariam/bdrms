package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.InsertionRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = InsertionRequest.class)
@Transactional(readOnly = true)
public interface InsertionRequestRepository extends DetachableJpaRepository<InsertionRequest, Long>, InsertionRequestRepositoryCustom {
}
