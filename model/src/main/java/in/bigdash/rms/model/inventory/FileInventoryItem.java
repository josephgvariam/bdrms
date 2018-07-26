package in.bigdash.rms.model.inventory;
import in.bigdash.rms.model.File;
import io.springlets.format.EntityFormat;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@EntityFormat
@Audited
@DiscriminatorValue("FILE")
public class FileInventoryItem extends InventoryItem {

    @NotNull
    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "FILE_ID")
    @EntityFormat
    private File file;


    public File getFile() {
        return this.file;
    }


    public FileInventoryItem setFile(File file) {
        this.file = file;
        return this;
    }

    @Override
    public String getFileBarcode() {
        return file != null ? file.getBarcode() : null;
    }

    @Override
    public String getBoxBarcode() {
        return file != null && file.getBox() != null ? file.getBox().getBarcode() : null;
    }

    @Override
    public String getShelfBarcode() {
        return file != null && file.getBox() != null && file.getBox().getShelf() != null ? file.getBox().getShelf().getBarcode() : null;
    }

    @Override
    public String getLocation() {
        return file != null ? file.getLocation() : null;
    }

    @Override
    public void setLocation(String location) {
        if (file != null) {
            file.setLocation(location);
        }
    }
}
