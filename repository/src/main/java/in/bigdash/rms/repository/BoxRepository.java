package in.bigdash.rms.repository;
import in.bigdash.rms.model.Box;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Shelf;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = Box.class)
@Transactional(readOnly = true)
public interface BoxRepository extends DetachableJpaRepository<Box, Long>, BoxRepositoryCustom {


    public abstract long countByShelf(Shelf shelf);
}
