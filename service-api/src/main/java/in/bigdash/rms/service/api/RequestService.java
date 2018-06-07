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


@RooService(entity = Request.class)
public interface RequestService extends EntityResolver<Request, Long>, ValidatorService<Request> {


    public abstract Request findOne(Long id);


    public abstract void delete(Request request);


    public abstract List<Request> save(Iterable<Request> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract Request save(Request entity);


    public abstract Request findOneForUpdate(Long id);


    public abstract List<Request> findAll(Iterable<Long> ids);


    public abstract List<Request> findAll();


    public abstract long count();


    public abstract Page<Request> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Request> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Request addToInventoryItems(Request request, Iterable<Long> inventoryItemsToAdd);


    public abstract Request removeFromInventoryItems(Request request, Iterable<Long> inventoryItemsToRemove);


    public abstract Request setInventoryItems(Request request, Iterable<Long> inventoryItems);


    public abstract Page<Request> findByStorageType(StorageType storageType, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Request> findByUserAssigned(User userAssigned, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Request> findByUserCreated(User userCreated, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByStorageType(StorageType storageType);


    public abstract long countByUserAssigned(User userAssigned);


    public abstract long countByUserCreated(User userCreated);
}
