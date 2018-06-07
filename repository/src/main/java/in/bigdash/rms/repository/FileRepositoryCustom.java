package in.bigdash.rms.repository;
import in.bigdash.rms.model.File;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.Box;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = File.class)
public interface FileRepositoryCustom {


    public abstract Page<File> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<File> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<File> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
