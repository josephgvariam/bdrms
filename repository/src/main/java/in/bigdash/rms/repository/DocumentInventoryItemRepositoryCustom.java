package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.Document;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = DocumentInventoryItemRepositoryCustom
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = DocumentInventoryItem.class)
public interface DocumentInventoryItemRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param document
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<DocumentInventoryItem> findByDocument(Document document, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<DocumentInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<DocumentInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
