package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.Request;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = RequestRepositoryCustom
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = Request.class)
public interface RequestRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param userAssigned
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Request> findByUserAssigned(User userAssigned, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Request> findByStorageType(StorageType storageType, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param userCreated
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Request> findByUserCreated(User userCreated, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Request> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Request> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
