package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.RetrievalRequest;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = RetrievalRequest.class)
public interface RetrievalRequestRepositoryCustom {


    public abstract Page<RetrievalRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<RetrievalRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
