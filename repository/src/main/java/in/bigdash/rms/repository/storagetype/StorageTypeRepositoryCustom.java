package in.bigdash.rms.repository.storagetype;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.Client;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface StorageTypeRepositoryCustom {


    public abstract Page<StorageType> findByClientsContains(Client clients, GlobalSearch globalSearch, Pageable pageable);

    public abstract Page<StorageType> findAll(GlobalSearch globalSearch, Pageable pageable);

    public Page<StorageType> findAllByCurrentUser(GlobalSearch globalSearch, Pageable pageable);

    public abstract Page<StorageType> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
