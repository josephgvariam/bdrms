package in.bigdash.rms.repository;
import in.bigdash.rms.model.Shelf;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.Facility;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = Shelf.class)
public interface ShelfRepositoryCustom {


    public abstract Page<Shelf> findByFacility(Facility facility, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Shelf> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Shelf> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
