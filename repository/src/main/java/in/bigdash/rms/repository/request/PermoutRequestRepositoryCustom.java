package in.bigdash.rms.repository.request;
import in.bigdash.rms.model.request.PermoutRequest;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PermoutRequestRepositoryCustom {


    public abstract Page<PermoutRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<PermoutRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
