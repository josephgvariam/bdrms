package in.bigdash.rms.repository;
import in.bigdash.rms.model.Role;
import in.bigdash.rms.model.User;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RoleRepositoryCustom {


    public abstract Page<Role> findByUsersContains(User users, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Role> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Role> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
