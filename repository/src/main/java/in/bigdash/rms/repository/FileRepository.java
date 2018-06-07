package in.bigdash.rms.repository;
import in.bigdash.rms.model.File;
import io.springlets.data.jpa.repository.DetachableJpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepository;
import in.bigdash.rms.model.Box;
import org.springframework.transaction.annotation.Transactional;

/**
 * = FileRepository
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepository(entity = File.class)
@Transactional(readOnly = true)
public interface FileRepository extends DetachableJpaRepository<File, Long>, FileRepositoryCustom {

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @return Long
     */
    public abstract long countByBox(Box box);
}
