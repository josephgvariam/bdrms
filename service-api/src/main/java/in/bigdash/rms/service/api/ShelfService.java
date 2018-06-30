package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Shelf;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import in.bigdash.rms.model.Facility;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ShelfService extends EntityResolver<Shelf, Long>, ValidatorService<Shelf> {


    public abstract Shelf findOne(Long id);

    public abstract Shelf findByBarcode(String barcode);


    public abstract void delete(Shelf shelf);


    public abstract List<Shelf> save(Iterable<Shelf> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract Shelf save(Shelf entity);


    public abstract Shelf findOneForUpdate(Long id);


    public abstract List<Shelf> findAll(Iterable<Long> ids);


    public abstract List<Shelf> findAll();


    public abstract long count();


    public abstract Page<Shelf> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Shelf> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Shelf addToBoxes(Shelf shelf, Iterable<Long> boxesToAdd);


    public abstract Shelf removeFromBoxes(Shelf shelf, Iterable<Long> boxesToRemove);


    public abstract Shelf setBoxes(Shelf shelf, Iterable<Long> boxes);


    public abstract Page<Shelf> findByFacility(Facility facility, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByFacility(Facility facility);
}
