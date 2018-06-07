package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Box;
import org.springframework.transaction.annotation.Transactional;

/**
 * = BoxInventoryItemRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = BoxInventoryItem.class)
@Transactional(readOnly = true)
public interface BoxInventoryItemRepository extends DetachableJpaRepository<BoxInventoryItem, Long>, BoxInventoryItemRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @return Long
     */
    public abstract long countByBox(Box box);
}
