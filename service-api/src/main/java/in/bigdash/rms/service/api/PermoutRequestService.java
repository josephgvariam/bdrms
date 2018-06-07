package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.PermoutRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = PermoutRequestService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = PermoutRequest.class)
public interface PermoutRequestService extends EntityResolver<PermoutRequest, Long>, ValidatorService<PermoutRequest> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return PermoutRequest
     */
    public abstract PermoutRequest findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param permoutRequest
     */
    public abstract void delete(PermoutRequest permoutRequest);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<PermoutRequest> save(Iterable<PermoutRequest> entities);

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
     * @return PermoutRequest
     */
    public abstract PermoutRequest save(PermoutRequest entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return PermoutRequest
     */
    public abstract PermoutRequest findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<PermoutRequest> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<PermoutRequest> findAll();

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
    public abstract Page<PermoutRequest> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<PermoutRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
