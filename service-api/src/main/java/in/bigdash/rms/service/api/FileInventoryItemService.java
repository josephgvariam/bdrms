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


@RooService(entity = FileInventoryItem.class)
public interface FileInventoryItemService extends EntityResolver<FileInventoryItem, Long>, ValidatorService<FileInventoryItem> {


    public abstract FileInventoryItem findOne(Long id);


    public abstract void delete(FileInventoryItem fileInventoryItem);


    public abstract List<FileInventoryItem> save(Iterable<FileInventoryItem> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract FileInventoryItem save(FileInventoryItem entity);


    public abstract FileInventoryItem findOneForUpdate(Long id);


    public abstract List<FileInventoryItem> findAll(Iterable<Long> ids);


    public abstract List<FileInventoryItem> findAll();


    public abstract long count();


    public abstract Page<FileInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<FileInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<FileInventoryItem> findByFile(File file, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByFile(File file);
}
