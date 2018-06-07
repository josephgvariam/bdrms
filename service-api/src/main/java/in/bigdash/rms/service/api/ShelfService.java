package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Shelf;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import in.bigdash.rms.model.Facility;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = ShelfService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Shelf.class)
public interface ShelfService extends EntityResolver<Shelf, Long>, ValidatorService<Shelf> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Shelf
     */
    public abstract Shelf findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     */
    public abstract void delete(Shelf shelf);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<Shelf> save(Iterable<Shelf> entities);

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
     * @return Shelf
     */
    public abstract Shelf save(Shelf entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Shelf
     */
    public abstract Shelf findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<Shelf> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<Shelf> findAll();

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
    public abstract Page<Shelf> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Shelf> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param boxesToAdd
     * @return Shelf
     */
    public abstract Shelf addToBoxes(Shelf shelf, Iterable<Long> boxesToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param boxesToRemove
     * @return Shelf
     */
    public abstract Shelf removeFromBoxes(Shelf shelf, Iterable<Long> boxesToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param boxes
     * @return Shelf
     */
    public abstract Shelf setBoxes(Shelf shelf, Iterable<Long> boxes);

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Shelf> findByFacility(Facility facility, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @return Long
     */
    public abstract long countByFacility(Facility facility);
}
