package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Facility;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = FacilityService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Facility.class)
public interface FacilityService extends EntityResolver<Facility, Long>, ValidatorService<Facility> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Facility
     */
    public abstract Facility findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     */
    public abstract void delete(Facility facility);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<Facility> save(Iterable<Facility> entities);

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
     * @return Facility
     */
    public abstract Facility save(Facility entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Facility
     */
    public abstract Facility findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<Facility> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<Facility> findAll();

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
    public abstract Page<Facility> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Facility> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param shelvesToAdd
     * @return Facility
     */
    public abstract Facility addToShelves(Facility facility, Iterable<Long> shelvesToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param shelvesToRemove
     * @return Facility
     */
    public abstract Facility removeFromShelves(Facility facility, Iterable<Long> shelvesToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param shelves
     * @return Facility
     */
    public abstract Facility setShelves(Facility facility, Iterable<Long> shelves);
}
