package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.RetrievalRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = RetrievalRequestService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = RetrievalRequest.class)
public interface RetrievalRequestService extends EntityResolver<RetrievalRequest, Long>, ValidatorService<RetrievalRequest> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return RetrievalRequest
     */
    public abstract RetrievalRequest findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequest
     */
    public abstract void delete(RetrievalRequest retrievalRequest);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<RetrievalRequest> save(Iterable<RetrievalRequest> entities);

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
     * @return RetrievalRequest
     */
    public abstract RetrievalRequest save(RetrievalRequest entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return RetrievalRequest
     */
    public abstract RetrievalRequest findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<RetrievalRequest> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<RetrievalRequest> findAll();

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
    public abstract Page<RetrievalRequest> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<RetrievalRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
