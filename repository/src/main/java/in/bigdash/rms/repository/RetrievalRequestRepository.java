package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.RetrievalRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * = RetrievalRequestRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = RetrievalRequest.class)
@Transactional(readOnly = true)
public interface RetrievalRequestRepository extends DetachableJpaRepository<RetrievalRequest, Long>, RetrievalRequestRepositoryCustom {
}
