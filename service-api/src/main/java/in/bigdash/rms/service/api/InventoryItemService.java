package in.bigdash.rms.service.api;
import in.bigdash.rms.model.inventory.InventoryItem;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import in.bigdash.rms.model.request.Request;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface InventoryItemService extends EntityResolver<InventoryItem, Long>, ValidatorService<InventoryItem> {


    public abstract InventoryItem findOne(Long id);


    public abstract void delete(InventoryItem inventoryItem);


    public abstract List<InventoryItem> save(Iterable<InventoryItem> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract InventoryItem save(InventoryItem entity);


    public abstract InventoryItem findOneForUpdate(Long id);


    public abstract List<InventoryItem> findAll(Iterable<Long> ids);


    public abstract List<InventoryItem> findAll();


    public abstract long count();


    public abstract Page<InventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);

    public abstract List<InventoryItem> findByRequestTypeAndStorageType(String requestType, String storageType, Long requestId, Long fromFacilityId);

    public abstract Page<InventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<InventoryItem> findByRequestsContains(Request requests, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByRequestsContains(Request requests);
}
