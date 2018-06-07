package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import in.bigdash.rms.model.Document;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DocumentInventoryItemRepositoryCustom {


    public abstract Page<DocumentInventoryItem> findByDocument(Document document, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<DocumentInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<DocumentInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
