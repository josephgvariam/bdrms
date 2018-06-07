package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.PermoutRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooService(entity = PermoutRequest.class)
public interface PermoutRequestService extends EntityResolver<PermoutRequest, Long>, ValidatorService<PermoutRequest> {


    public abstract PermoutRequest findOne(Long id);


    public abstract void delete(PermoutRequest permoutRequest);


    public abstract List<PermoutRequest> save(Iterable<PermoutRequest> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract PermoutRequest save(PermoutRequest entity);


    public abstract PermoutRequest findOneForUpdate(Long id);


    public abstract List<PermoutRequest> findAll(Iterable<Long> ids);


    public abstract List<PermoutRequest> findAll();


    public abstract long count();


    public abstract Page<PermoutRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<PermoutRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
