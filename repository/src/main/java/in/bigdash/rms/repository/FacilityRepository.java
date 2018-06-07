package in.bigdash.rms.repository;
import in.bigdash.rms.model.Facility;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * = FacilityRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Facility.class)
@Transactional(readOnly = true)
public interface FacilityRepository extends DetachableJpaRepository<Facility, Long>, FacilityRepositoryCustom {
}
