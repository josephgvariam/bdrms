package in.bigdash.rms.model.inventory;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import in.bigdash.rms.model.File;
import io.springlets.format.EntityFormat;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Entity;

/**
 * = FileInventoryItem
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity
@Entity
@EntityFormat
public class FileInventoryItem extends InventoryItem {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    @EntityFormat
    private File file;

    /**
     * Gets file value
     *
     * @return File
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Sets file value
     *
     * @param file
     * @return FileInventoryItem
     */
    public FileInventoryItem setFile(File file) {
        this.file = file;
        return this;
    }
}
