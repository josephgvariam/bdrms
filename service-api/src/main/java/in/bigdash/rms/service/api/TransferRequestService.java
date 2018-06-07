package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.TransferRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = TransferRequestService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = TransferRequest.class)
public interface TransferRequestService extends EntityResolver<TransferRequest, Long>, ValidatorService<TransferRequest> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return TransferRequest
     */
    public abstract TransferRequest findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequest
     */
    public abstract void delete(TransferRequest transferRequest);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<TransferRequest> save(Iterable<TransferRequest> entities);

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
     * @return TransferRequest
     */
    public abstract TransferRequest save(TransferRequest entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return TransferRequest
     */
    public abstract TransferRequest findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<TransferRequest> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<TransferRequest> findAll();

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
    public abstract Page<TransferRequest> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<TransferRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
