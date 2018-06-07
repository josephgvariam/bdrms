package in.bigdash.rms.repository;
import in.bigdash.rms.model.Document;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.File;
import org.springframework.transaction.annotation.Transactional;

/**
 * = DocumentRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = Document.class)
@Transactional(readOnly = true)
public interface DocumentRepository extends DetachableJpaRepository<Document, Long>, DocumentRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @return Long
     */
    public abstract long countByFile(File file);
}
