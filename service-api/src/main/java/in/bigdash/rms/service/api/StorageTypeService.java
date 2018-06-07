package in.bigdash.rms.service.api;
import in.bigdash.rms.model.StorageType;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.Client;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = StorageTypeService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = StorageType.class)
public interface StorageTypeService extends EntityResolver<StorageType, Long>, ValidatorService<StorageType> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return StorageType
     */
    public abstract StorageType findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     */
    public abstract void delete(StorageType storageType);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<StorageType> save(Iterable<StorageType> entities);

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
     * @return StorageType
     */
    public abstract StorageType save(StorageType entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return StorageType
     */
    public abstract StorageType findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<StorageType> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<StorageType> findAll();

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
    public abstract Page<StorageType> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<StorageType> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param requestsToAdd
     * @return StorageType
     */
    public abstract StorageType addToRequests(StorageType storageType, Iterable<Long> requestsToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param requestsToRemove
     * @return StorageType
     */
    public abstract StorageType removeFromRequests(StorageType storageType, Iterable<Long> requestsToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param requests
     * @return StorageType
     */
    public abstract StorageType setRequests(StorageType storageType, Iterable<Long> requests);

    /**
     * TODO Auto-generated method documentation
     *
     * @param clients
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<StorageType> findByClientsContains(Client clients, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param clients
     * @return Long
     */
    public abstract long countByClientsContains(Client clients);
}
