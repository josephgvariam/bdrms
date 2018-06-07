package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.DestructionRequest;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DestructionRequestRepositoryCustom {


    public abstract Page<DestructionRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<DestructionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
