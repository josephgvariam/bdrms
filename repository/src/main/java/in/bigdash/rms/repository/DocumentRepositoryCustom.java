package in.bigdash.rms.repository;
import in.bigdash.rms.model.Document;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustom;
import in.bigdash.rms.model.File;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RooJpaRepositoryCustom(entity = Document.class)
public interface DocumentRepositoryCustom {


    public abstract Page<Document> findByFile(File file, GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Document> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Document> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);
}
