package in.bigdash.rms.repository.user;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.Client;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserRepositoryCustom {


    public abstract Page<User> findByClient(Client client, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<User> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<User> findAllByCurrentUserClient(GlobalSearch globalSearch, Pageable pageable);

    public abstract Page<User> findAllOperators(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<User> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
