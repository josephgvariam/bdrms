package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.Box;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = BoxInventoryItem.class)
public interface BoxInventoryItemRepositoryCustom {


    public abstract Page<BoxInventoryItem> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<BoxInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<BoxInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
