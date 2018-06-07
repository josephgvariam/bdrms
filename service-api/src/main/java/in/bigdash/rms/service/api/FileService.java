package in.bigdash.rms.service.api;
import in.bigdash.rms.model.File;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.Box;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = FileService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = File.class)
public interface FileService extends EntityResolver<File, Long>, ValidatorService<File> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return File
     */
    public abstract File findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     */
    public abstract void delete(File file);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<File> save(Iterable<File> entities);

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
     * @return File
     */
    public abstract File save(File entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return File
     */
    public abstract File findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<File> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<File> findAll();

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
    public abstract Page<File> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<File> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param documentsToAdd
     * @return File
     */
    public abstract File addToDocuments(File file, Iterable<Long> documentsToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param documentsToRemove
     * @return File
     */
    public abstract File removeFromDocuments(File file, Iterable<Long> documentsToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param documents
     * @return File
     */
    public abstract File setDocuments(File file, Iterable<Long> documents);

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<File> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @return Long
     */
    public abstract long countByBox(Box box);
}
