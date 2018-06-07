package in.bigdash.rms.repository.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.File;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface FileInventoryItemRepository extends DetachableJpaRepository<FileInventoryItem, Long>, FileInventoryItemRepositoryCustom {


    public abstract long countByFile(File file);
}
