package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.Document;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface DocumentInventoryItemRepository extends DetachableJpaRepository<DocumentInventoryItem, Long>, DocumentInventoryItemRepositoryCustom {


    public abstract long countByDocument(Document document);
}
