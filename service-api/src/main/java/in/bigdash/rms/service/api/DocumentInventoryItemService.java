package in.bigdash.rms.service.api;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.Document;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooService(entity = DocumentInventoryItem.class)
public interface DocumentInventoryItemService extends EntityResolver<DocumentInventoryItem, Long>, ValidatorService<DocumentInventoryItem> {


    public abstract DocumentInventoryItem findOne(Long id);


    public abstract void delete(DocumentInventoryItem documentInventoryItem);


    public abstract List<DocumentInventoryItem> save(Iterable<DocumentInventoryItem> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract DocumentInventoryItem save(DocumentInventoryItem entity);


    public abstract DocumentInventoryItem findOneForUpdate(Long id);


    public abstract List<DocumentInventoryItem> findAll(Iterable<Long> ids);


    public abstract List<DocumentInventoryItem> findAll();


    public abstract long count();


    public abstract Page<DocumentInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<DocumentInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<DocumentInventoryItem> findByDocument(Document document, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByDocument(Document document);
}
