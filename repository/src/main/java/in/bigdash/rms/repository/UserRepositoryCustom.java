package in.bigdash.rms.repository;
import in.bigdash.rms.model.User;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.Client;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = UserRepositoryCustom
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = User.class)
public interface UserRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<User> findByClient(Client client, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<User> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<User> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
