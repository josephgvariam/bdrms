package in.bigdash.rms.repository;
import in.bigdash.rms.model.Facility;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface FacilityRepository extends DetachableJpaRepository<Facility, Long>, FacilityRepositoryCustom {
}
