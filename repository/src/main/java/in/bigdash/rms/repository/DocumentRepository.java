package in.bigdash.rms.repository;
import in.bigdash.rms.model.Document;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.File;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(entity = Document.class)
@Transactional(readOnly = true)
public interface DocumentRepository extends DetachableJpaRepository<Document, Long>, DocumentRepositoryCustom {


    public abstract long countByFile(File file);
}
