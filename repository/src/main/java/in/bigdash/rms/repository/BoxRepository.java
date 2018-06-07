package in.bigdash.rms.repository;
import in.bigdash.rms.model.Box;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Shelf;
import org.springframework.transaction.annotation.Transactional;

/**
 * = BoxRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Box.class)
@Transactional(readOnly = true)
public interface BoxRepository extends DetachableJpaRepository<Box, Long>, BoxRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @return Long
     */
    public abstract long countByShelf(Shelf shelf);
}
