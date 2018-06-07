package in.bigdash.rms.service.api;
import in.bigdash.rms.model.inventory.InventoryItem;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.request.Request;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = InventoryItemService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = InventoryItem.class)
public interface InventoryItemService extends EntityResolver<InventoryItem, Long>, ValidatorService<InventoryItem> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return InventoryItem
     */
    public abstract InventoryItem findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItem
     */
    public abstract void delete(InventoryItem inventoryItem);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<InventoryItem> save(Iterable<InventoryItem> entities);

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
     * @return InventoryItem
     */
    public abstract InventoryItem save(InventoryItem entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return InventoryItem
     */
    public abstract InventoryItem findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<InventoryItem> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<InventoryItem> findAll();

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
    public abstract Page<InventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<InventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param requests
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<InventoryItem> findByRequestsContains(Request requests, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param requests
     * @return Long
     */
    public abstract long countByRequestsContains(Request requests);
}
