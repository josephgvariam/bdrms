package in.bigdash.rms.repository.request;
import in.bigdash.rms.model.request.InsertionRequest;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface InsertionRequestRepositoryCustom {


    public abstract Page<InsertionRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<InsertionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
