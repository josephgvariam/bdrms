package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.RefilingRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = RefilingRequestService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = RefilingRequest.class)
public interface RefilingRequestService extends EntityResolver<RefilingRequest, Long>, ValidatorService<RefilingRequest> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return RefilingRequest
     */
    public abstract RefilingRequest findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param refilingRequest
     */
    public abstract void delete(RefilingRequest refilingRequest);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<RefilingRequest> save(Iterable<RefilingRequest> entities);

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
     * @return RefilingRequest
     */
    public abstract RefilingRequest save(RefilingRequest entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return RefilingRequest
     */
    public abstract RefilingRequest findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<RefilingRequest> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<RefilingRequest> findAll();

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
    public abstract Page<RefilingRequest> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<RefilingRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
