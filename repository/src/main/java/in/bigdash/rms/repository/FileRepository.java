package in.bigdash.rms.repository;
import in.bigdash.rms.model.File;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import in.bigdash.rms.model.Box;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface FileRepository extends DetachableJpaRepository<File, Long>, FileRepositoryCustom {


    public abstract long countByBox(Box box);
}
