package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.DestructionRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = DestructionRequestService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = DestructionRequest.class)
public interface DestructionRequestService extends EntityResolver<DestructionRequest, Long>, ValidatorService<DestructionRequest> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return DestructionRequest
     */
    public abstract DestructionRequest findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     */
    public abstract void delete(DestructionRequest destructionRequest);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<DestructionRequest> save(Iterable<DestructionRequest> entities);

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
     * @return DestructionRequest
     */
    public abstract DestructionRequest save(DestructionRequest entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return DestructionRequest
     */
    public abstract DestructionRequest findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<DestructionRequest> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<DestructionRequest> findAll();

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
    public abstract Page<DestructionRequest> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<DestructionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
