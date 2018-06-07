package in.bigdash.rms.service.api;
import in.bigdash.rms.model.File;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import in.bigdash.rms.model.Box;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FileService extends EntityResolver<File, Long>, ValidatorService<File> {


    public abstract File findOne(Long id);


    public abstract void delete(File file);


    public abstract List<File> save(Iterable<File> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract File save(File entity);


    public abstract File findOneForUpdate(Long id);


    public abstract List<File> findAll(Iterable<Long> ids);


    public abstract List<File> findAll();


    public abstract long count();


    public abstract Page<File> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<File> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract File addToDocuments(File file, Iterable<Long> documentsToAdd);


    public abstract File removeFromDocuments(File file, Iterable<Long> documentsToRemove);


    public abstract File setDocuments(File file, Iterable<Long> documents);


    public abstract Page<File> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable);


    public abstract long countByBox(Box box);
}
