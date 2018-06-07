package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.PickupRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = PickupRequestService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = PickupRequest.class)
public interface PickupRequestService extends EntityResolver<PickupRequest, Long>, ValidatorService<PickupRequest> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return PickupRequest
     */
    public abstract PickupRequest findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param pickupRequest
     */
    public abstract void delete(PickupRequest pickupRequest);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<PickupRequest> save(Iterable<PickupRequest> entities);

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
     * @return PickupRequest
     */
    public abstract PickupRequest save(PickupRequest entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return PickupRequest
     */
    public abstract PickupRequest findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<PickupRequest> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<PickupRequest> findAll();

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
    public abstract Page<PickupRequest> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<PickupRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
