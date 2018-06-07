package in.bigdash.rms.service.api;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.Document;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = DocumentInventoryItemService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = DocumentInventoryItem.class)
public interface DocumentInventoryItemService extends EntityResolver<DocumentInventoryItem, Long>, ValidatorService<DocumentInventoryItem> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return DocumentInventoryItem
     */
    public abstract DocumentInventoryItem findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     */
    public abstract void delete(DocumentInventoryItem documentInventoryItem);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<DocumentInventoryItem> save(Iterable<DocumentInventoryItem> entities);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    public abstract void delete(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return DocumentInventoryItem
     */
    public abstract DocumentInventoryItem save(DocumentInventoryItem entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return DocumentInventoryItem
     */
    public abstract DocumentInventoryItem findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<DocumentInventoryItem> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<DocumentInventoryItem> findAll();

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public abstract long count();

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
     * @param document
     * @return Long
     */
    public abstract long countByDocument(Document document);
}
