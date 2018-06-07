package in.bigdash.rms.repository;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import in.bigdash.rms.model.File;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FileInventoryItemRepositoryCustom {


    public abstract Page<FileInventoryItem> findByFile(File file, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<FileInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<FileInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
