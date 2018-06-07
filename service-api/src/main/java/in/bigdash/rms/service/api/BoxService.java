package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Box;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import in.bigdash.rms.model.Shelf;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BoxService extends EntityResolver<Box, Long>, ValidatorService<Box> {


    public abstract Box findOne(Long id);


    public abstract void delete(Box box);


    public abstract List<Box> save(Iterable<Box> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract Box save(Box entity);


    public abstract Box findOneForUpdate(Long id);


    public abstract List<Box> findAll(Iterable<Long> ids);


    public abstract List<Box> findAll();


    public abstract long count();


    public abstract Page<Box> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Box> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Box addToFiles(Box box, Iterable<Long> filesToAdd);


    public abstract Box removeFromFiles(Box box, Iterable<Long> filesToRemove);


    public abstract Box setFiles(Box box, Iterable<Long> files);


    public abstract Page<Box> findByShelf(Shelf shelf, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByShelf(Shelf shelf);
}
