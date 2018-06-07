package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.TransferRequest;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TransferRequestRepositoryCustom {


    public abstract Page<TransferRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<TransferRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
