package in.bigdash.rms.repository.facility;
import in.bigdash.rms.model.Facility;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FacilityRepositoryCustom {


    public abstract Page<Facility> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Facility> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
