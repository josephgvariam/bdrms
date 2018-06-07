package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Document;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.File;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooService(entity = Document.class)
public interface DocumentService extends EntityResolver<Document, Long>, ValidatorService<Document> {


    public abstract Document findOne(Long id);


    public abstract void delete(Document document);


    public abstract List<Document> save(Iterable<Document> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract Document save(Document entity);


    public abstract Document findOneForUpdate(Long id);


    public abstract List<Document> findAll(Iterable<Long> ids);


    public abstract List<Document> findAll();


    public abstract long count();


    public abstract Page<Document> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Document> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Document> findByFile(File file, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByFile(File file);
}
