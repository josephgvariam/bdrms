package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.RetrievalRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RetrievalRequestService extends EntityResolver<RetrievalRequest, Long>, ValidatorService<RetrievalRequest> {


    public abstract RetrievalRequest findOne(Long id);


    public abstract void delete(RetrievalRequest retrievalRequest);


    public abstract List<RetrievalRequest> save(Iterable<RetrievalRequest> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract RetrievalRequest save(RetrievalRequest entity);


    public abstract RetrievalRequest findOneForUpdate(Long id);


    public abstract List<RetrievalRequest> findAll(Iterable<Long> ids);


    public abstract List<RetrievalRequest> findAll();


    public abstract long count();


    public abstract Page<RetrievalRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<RetrievalRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
