package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.RefilingRequest;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = RefilingRequest.class)
@Transactional(readOnly = true)
public interface RefilingRequestRepository extends DetachableJpaRepository<RefilingRequest, Long>, RefilingRequestRepositoryCustom {
}
