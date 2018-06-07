package in.bigdash.rms.service.api;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import in.bigdash.rms.model.Box;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BoxInventoryItemService extends EntityResolver<BoxInventoryItem, Long>, ValidatorService<BoxInventoryItem> {


    public abstract BoxInventoryItem findOne(Long id);


    public abstract void delete(BoxInventoryItem boxInventoryItem);


    public abstract List<BoxInventoryItem> save(Iterable<BoxInventoryItem> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract BoxInventoryItem save(BoxInventoryItem entity);


    public abstract BoxInventoryItem findOneForUpdate(Long id);


    public abstract List<BoxInventoryItem> findAll(Iterable<Long> ids);


    public abstract List<BoxInventoryItem> findAll();


    public abstract long count();


    public abstract Page<BoxInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<BoxInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<BoxInventoryItem> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByBox(Box box);
}
