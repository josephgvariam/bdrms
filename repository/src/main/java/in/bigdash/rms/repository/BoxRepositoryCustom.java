package in.bigdash.rms.repository;
import in.bigdash.rms.model.Box;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.Shelf;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = BoxRepositoryCustom
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustom(entity = Box.class)
public interface BoxRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Box> findByShelf(Shelf shelf, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Box> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Box> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
