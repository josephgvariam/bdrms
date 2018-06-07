package in.bigdash.rms.repository;
import in.bigdash.rms.model.StorageType;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.Client;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = StorageType.class)
public interface StorageTypeRepositoryCustom {


    public abstract Page<StorageType> findByClientsContains(Client clients, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<StorageType> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<StorageType> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
