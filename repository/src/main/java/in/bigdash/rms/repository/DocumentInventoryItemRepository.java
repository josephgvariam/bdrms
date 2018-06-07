package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Document;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = DocumentInventoryItem.class)
@Transactional(readOnly = true)
public interface DocumentInventoryItemRepository extends DetachableJpaRepository<DocumentInventoryItem, Long>, DocumentInventoryItemRepositoryCustom {


    public abstract long countByDocument(Document document);
}
