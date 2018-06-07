package in.bigdash.rms.repository;
import in.bigdash.rms.model.Document;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.File;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface DocumentRepository extends DetachableJpaRepository<Document, Long>, DocumentRepositoryCustom {


    public abstract long countByFile(File file);
}
