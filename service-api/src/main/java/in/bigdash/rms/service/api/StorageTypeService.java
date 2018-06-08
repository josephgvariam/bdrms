package in.bigdash.rms.service.api;
import in.bigdash.rms.model.StorageType;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import in.bigdash.rms.model.Client;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface StorageTypeService extends EntityResolver<StorageType, Long>, ValidatorService<StorageType> {


    public abstract StorageType findOne(Long id);


    public abstract void delete(StorageType storageType);


    public abstract List<StorageType> save(Iterable<StorageType> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract StorageType save(StorageType entity);


    public abstract StorageType findOneForUpdate(Long id);


    public abstract List<StorageType> findAll(Iterable<Long> ids);


    public abstract List<StorageType> findAll();


    public abstract long count();


    public abstract Page<StorageType> findAll(GlobalSearch globalSearch, Pageable pageable);

    public abstract Page<StorageType> findAllByCurrentUser(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<StorageType> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract StorageType addToRequests(StorageType storageType, Iterable<Long> requestsToAdd);


    public abstract StorageType removeFromRequests(StorageType storageType, Iterable<Long> requestsToRemove);


    public abstract StorageType setRequests(StorageType storageType, Iterable<Long> requests);


    public abstract Page<StorageType> findByClientsContains(Client clients, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByClientsContains(Client clients);
}
