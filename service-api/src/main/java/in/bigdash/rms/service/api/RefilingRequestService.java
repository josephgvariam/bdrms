package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.RefilingRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooService(entity = RefilingRequest.class)
public interface RefilingRequestService extends EntityResolver<RefilingRequest, Long>, ValidatorService<RefilingRequest> {


    public abstract RefilingRequest findOne(Long id);


    public abstract void delete(RefilingRequest refilingRequest);


    public abstract List<RefilingRequest> save(Iterable<RefilingRequest> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract RefilingRequest save(RefilingRequest entity);


    public abstract RefilingRequest findOneForUpdate(Long id);


    public abstract List<RefilingRequest> findAll(Iterable<Long> ids);


    public abstract List<RefilingRequest> findAll();


    public abstract long count();


    public abstract Page<RefilingRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<RefilingRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
