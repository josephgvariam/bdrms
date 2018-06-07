package in.bigdash.rms.repository;
import in.bigdash.rms.model.Shelf;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.Facility;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface ShelfRepository extends DetachableJpaRepository<Shelf, Long>, ShelfRepositoryCustom {


    public abstract long countByFacility(Facility facility);
}
