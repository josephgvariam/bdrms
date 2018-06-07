package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.RefilingRequest;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = RefilingRequest.class)
public interface RefilingRequestRepositoryCustom {


    public abstract Page<RefilingRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<RefilingRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
