package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Box;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = BoxInventoryItem.class)
@Transactional(readOnly = true)
public interface BoxInventoryItemRepository extends DetachableJpaRepository<BoxInventoryItem, Long>, BoxInventoryItemRepositoryCustom {


    public abstract long countByBox(Box box);
}
