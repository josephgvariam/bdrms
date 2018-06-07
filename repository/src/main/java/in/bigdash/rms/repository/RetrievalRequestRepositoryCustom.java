package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.RetrievalRequest;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RetrievalRequestRepositoryCustom {


    public abstract Page<RetrievalRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<RetrievalRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
