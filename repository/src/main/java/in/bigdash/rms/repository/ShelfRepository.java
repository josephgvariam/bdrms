package in.bigdash.rms.repository;
import in.bigdash.rms.model.Shelf;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Facility;
import org.springframework.transaction.annotation.Transactional;

/**
 * = ShelfRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Shelf.class)
@Transactional(readOnly = true)
public interface ShelfRepository extends DetachableJpaRepository<Shelf, Long>, ShelfRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @return Long
     */
    public abstract long countByFacility(Facility facility);
}
