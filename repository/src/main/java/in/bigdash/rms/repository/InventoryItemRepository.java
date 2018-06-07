package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.InventoryItem;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.request.Request;
import org.springframework.transaction.annotation.Transactional;

/**
 * = InventoryItemRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = InventoryItem.class)
@Transactional(readOnly = true)
public interface InventoryItemRepository extends DetachableJpaRepository<InventoryItem, Long>, InventoryItemRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param requests
     * @return Long
     */
    public abstract long countByRequestsContains(Request requests);
}
