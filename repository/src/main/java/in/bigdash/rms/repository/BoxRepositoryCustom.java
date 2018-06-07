package in.bigdash.rms.repository;
import in.bigdash.rms.model.Box;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.Shelf;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = Box.class)
public interface BoxRepositoryCustom {


    public abstract Page<Box> findByShelf(Shelf shelf, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Box> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Box> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
