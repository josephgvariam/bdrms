package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.DestructionRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooService(entity = DestructionRequest.class)
public interface DestructionRequestService extends EntityResolver<DestructionRequest, Long>, ValidatorService<DestructionRequest> {


    public abstract DestructionRequest findOne(Long id);


    public abstract void delete(DestructionRequest destructionRequest);


    public abstract List<DestructionRequest> save(Iterable<DestructionRequest> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract DestructionRequest save(DestructionRequest entity);


    public abstract DestructionRequest findOneForUpdate(Long id);


    public abstract List<DestructionRequest> findAll(Iterable<Long> ids);


    public abstract List<DestructionRequest> findAll();


    public abstract long count();


    public abstract Page<DestructionRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<DestructionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
