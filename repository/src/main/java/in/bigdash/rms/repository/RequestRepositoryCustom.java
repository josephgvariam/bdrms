package in.bigdash.rms.repository;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RequestRepositoryCustom {


    public abstract Page<Request> findByUserAssigned(User userAssigned, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Request> findByStorageType(StorageType storageType, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Request> findByUserCreated(User userCreated, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Request> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Request> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
