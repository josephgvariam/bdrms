package in.bigdash.rms.service.api;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.File;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = FileInventoryItemService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = FileInventoryItem.class)
public interface FileInventoryItemService extends EntityResolver<FileInventoryItem, Long>, ValidatorService<FileInventoryItem> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return FileInventoryItem
     */
    public abstract FileInventoryItem findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     */
    public abstract void delete(FileInventoryItem fileInventoryItem);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<FileInventoryItem> save(Iterable<FileInventoryItem> entities);

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
     * @return FileInventoryItem
     */
    public abstract FileInventoryItem save(FileInventoryItem entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return FileInventoryItem
     */
    public abstract FileInventoryItem findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<FileInventoryItem> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<FileInventoryItem> findAll();

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
    public abstract Page<FileInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<FileInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<FileInventoryItem> findByFile(File file, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @return Long
     */
    public abstract long countByFile(File file);
}
