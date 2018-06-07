package in.bigdash.rms.service.api;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.Box;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = BoxInventoryItemService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = BoxInventoryItem.class)
public interface BoxInventoryItemService extends EntityResolver<BoxInventoryItem, Long>, ValidatorService<BoxInventoryItem> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return BoxInventoryItem
     */
    public abstract BoxInventoryItem findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxInventoryItem
     */
    public abstract void delete(BoxInventoryItem boxInventoryItem);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<BoxInventoryItem> save(Iterable<BoxInventoryItem> entities);

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
     * @return BoxInventoryItem
     */
    public abstract BoxInventoryItem save(BoxInventoryItem entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return BoxInventoryItem
     */
    public abstract BoxInventoryItem findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<BoxInventoryItem> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<BoxInventoryItem> findAll();

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
    public abstract Page<BoxInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<BoxInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<BoxInventoryItem> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @return Long
     */
    public abstract long countByBox(Box box);
}
