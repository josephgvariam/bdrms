package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.InventoryItem;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.request.Request;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = InventoryItem.class)
public interface InventoryItemRepositoryCustom {


    public abstract Page<InventoryItem> findByRequestsContains(Request requests, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<InventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<InventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
