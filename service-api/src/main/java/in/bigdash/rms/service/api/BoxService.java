package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Box;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.Shelf;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = BoxService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Box.class)
public interface BoxService extends EntityResolver<Box, Long>, ValidatorService<Box> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Box
     */
    public abstract Box findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     */
    public abstract void delete(Box box);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<Box> save(Iterable<Box> entities);

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
     * @return Box
     */
    public abstract Box save(Box entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Box
     */
    public abstract Box findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<Box> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<Box> findAll();

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
    public abstract Page<Box> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Box> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @param filesToAdd
     * @return Box
     */
    public abstract Box addToFiles(Box box, Iterable<Long> filesToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @param filesToRemove
     * @return Box
     */
    public abstract Box removeFromFiles(Box box, Iterable<Long> filesToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @param files
     * @return Box
     */
    public abstract Box setFiles(Box box, Iterable<Long> files);

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Box> findByShelf(Shelf shelf, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @return Long
     */
    public abstract long countByShelf(Shelf shelf);
}
