package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.InsertionRequest;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = InsertionRequest.class)
public interface InsertionRequestRepositoryCustom {


    public abstract Page<InsertionRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<InsertionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
