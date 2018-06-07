package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.PermoutRequest;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = PermoutRequest.class)
public interface PermoutRequestRepositoryCustom {


    public abstract Page<PermoutRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<PermoutRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
