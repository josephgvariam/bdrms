package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Facility;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooService(entity = Facility.class)
public interface FacilityService extends EntityResolver<Facility, Long>, ValidatorService<Facility> {


    public abstract Facility findOne(Long id);


    public abstract void delete(Facility facility);


    public abstract List<Facility> save(Iterable<Facility> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract Facility save(Facility entity);


    public abstract Facility findOneForUpdate(Long id);


    public abstract List<Facility> findAll(Iterable<Long> ids);


    public abstract List<Facility> findAll();


    public abstract long count();


    public abstract Page<Facility> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Facility> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Facility addToShelves(Facility facility, Iterable<Long> shelvesToAdd);


    public abstract Facility removeFromShelves(Facility facility, Iterable<Long> shelvesToRemove);


    public abstract Facility setShelves(Facility facility, Iterable<Long> shelves);
}
