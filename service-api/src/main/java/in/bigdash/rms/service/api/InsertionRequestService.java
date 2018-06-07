package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.InsertionRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = InsertionRequestService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = InsertionRequest.class)
public interface InsertionRequestService extends EntityResolver<InsertionRequest, Long>, ValidatorService<InsertionRequest> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return InsertionRequest
     */
    public abstract InsertionRequest findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequest
     */
    public abstract void delete(InsertionRequest insertionRequest);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<InsertionRequest> save(Iterable<InsertionRequest> entities);

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
     * @return InsertionRequest
     */
    public abstract InsertionRequest save(InsertionRequest entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return InsertionRequest
     */
    public abstract InsertionRequest findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<InsertionRequest> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<InsertionRequest> findAll();

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
    public abstract Page<InsertionRequest> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<InsertionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
