package in.bigdash.rms.repository;
import in.bigdash.rms.model.Client;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = Client.class)
public interface ClientRepositoryCustom {


    public abstract Page<Client> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Client> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
