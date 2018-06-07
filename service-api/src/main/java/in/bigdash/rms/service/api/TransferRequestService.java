package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.TransferRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooService(entity = TransferRequest.class)
public interface TransferRequestService extends EntityResolver<TransferRequest, Long>, ValidatorService<TransferRequest> {


    public abstract TransferRequest findOne(Long id);


    public abstract void delete(TransferRequest transferRequest);


    public abstract List<TransferRequest> save(Iterable<TransferRequest> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract TransferRequest save(TransferRequest entity);


    public abstract TransferRequest findOneForUpdate(Long id);


    public abstract List<TransferRequest> findAll(Iterable<Long> ids);


    public abstract List<TransferRequest> findAll();


    public abstract long count();


    public abstract Page<TransferRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<TransferRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
