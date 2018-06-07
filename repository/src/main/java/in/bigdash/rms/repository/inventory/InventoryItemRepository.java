package in.bigdash.rms.repository.inventory;
import in.bigdash.rms.model.inventory.InventoryItem;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.request.Request;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface InventoryItemRepository extends DetachableJpaRepository<InventoryItem, Long>, InventoryItemRepositoryCustom {


    public abstract long countByRequestsContains(Request requests);
}
