package in.bigdash.rms.repository;
import in.bigdash.rms.model.Document;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.File;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = DocumentRepositoryCustom
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = Document.class)
public interface DocumentRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Document> findByFile(File file, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Document> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Document> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
