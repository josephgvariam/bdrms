package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.Request;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = RequestService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Request.class)
public interface RequestService extends EntityResolver<Request, Long>, ValidatorService<Request> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Request
     */
    public abstract Request findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     */
    public abstract void delete(Request request);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<Request> save(Iterable<Request> entities);

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
     * @return Request
     */
    public abstract Request save(Request entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Request
     */
    public abstract Request findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<Request> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<Request> findAll();

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
    public abstract Page<Request> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Request> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @param inventoryItemsToAdd
     * @return Request
     */
    public abstract Request addToInventoryItems(Request request, Iterable<Long> inventoryItemsToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @param inventoryItemsToRemove
     * @return Request
     */
    public abstract Request removeFromInventoryItems(Request request, Iterable<Long> inventoryItemsToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @param inventoryItems
     * @return Request
     */
    public abstract Request setInventoryItems(Request request, Iterable<Long> inventoryItems);

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Request> findByStorageType(StorageType storageType, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param userAssigned
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Request> findByUserAssigned(User userAssigned, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param userCreated
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Request> findByUserCreated(User userCreated, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @return Long
     */
    public abstract long countByStorageType(StorageType storageType);

    /**
     * TODO Auto-generated method documentation
     *
     * @param userAssigned
     * @return Long
     */
    public abstract long countByUserAssigned(User userAssigned);

    /**
     * TODO Auto-generated method documentation
     *
     * @param userCreated
     * @return Long
     */
    public abstract long countByUserCreated(User userCreated);
}
