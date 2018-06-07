package in.bigdash.rms.service.api;
import in.bigdash.rms.model.request.PickupRequest;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooService(entity = PickupRequest.class)
public interface PickupRequestService extends EntityResolver<PickupRequest, Long>, ValidatorService<PickupRequest> {


    public abstract PickupRequest findOne(Long id);


    public abstract void delete(PickupRequest pickupRequest);


    public abstract List<PickupRequest> save(Iterable<PickupRequest> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract PickupRequest save(PickupRequest entity);


    public abstract PickupRequest findOneForUpdate(Long id);


    public abstract List<PickupRequest> findAll(Iterable<Long> ids);


    public abstract List<PickupRequest> findAll();


    public abstract long count();


    public abstract Page<PickupRequest> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<PickupRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
