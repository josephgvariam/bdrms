package in.bigdash.rms.repository.inventory;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.Box;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface BoxInventoryItemRepository extends DetachableJpaRepository<BoxInventoryItem, Long>, BoxInventoryItemRepositoryCustom {


    public abstract long countByBox(Box box);
}
