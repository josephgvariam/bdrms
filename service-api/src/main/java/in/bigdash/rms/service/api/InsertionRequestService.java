package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.InsertionRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooService(entity = InsertionRequest.class)
public interface InsertionRequestService extends EntityResolver<InsertionRequest, Long>, ValidatorService<InsertionRequest> {


    public abstract InsertionRequest findOne(Long id);


    public abstract void delete(InsertionRequest insertionRequest);


    public abstract List<InsertionRequest> save(Iterable<InsertionRequest> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract InsertionRequest save(InsertionRequest entity);


    public abstract InsertionRequest findOneForUpdate(Long id);


    public abstract List<InsertionRequest> findAll(Iterable<Long> ids);


    public abstract List<InsertionRequest> findAll();


    public abstract long count();


    public abstract Page<InsertionRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<InsertionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
