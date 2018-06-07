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

/**
 * = DocumentService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Document.class)
public interface DocumentService extends EntityResolver<Document, Long>, ValidatorService<Document> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Document
     */
    public abstract Document findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param document
     */
    public abstract void delete(Document document);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<Document> save(Iterable<Document> entities);

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
     * @return Document
     */
    public abstract Document save(Document entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Document
     */
    public abstract Document findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<Document> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<Document> findAll();

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
    public abstract Page<Document> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Document> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Document> findByFile(File file, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @return Long
     */
    public abstract long countByFile(File file);
}
