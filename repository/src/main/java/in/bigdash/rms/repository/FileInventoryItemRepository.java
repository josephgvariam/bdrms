package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.File;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = FileInventoryItem.class)
@Transactional(readOnly = true)
public interface FileInventoryItemRepository extends DetachableJpaRepository<FileInventoryItem, Long>, FileInventoryItemRepositoryCustom {


    public abstract long countByFile(File file);
}
