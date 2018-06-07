package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.PickupRequest;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PickupRequestRepositoryCustom {


    public abstract Page<PickupRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<PickupRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
