package in.bigdash.rms.model.inventory;
import in.bigdash.rms.model.Box;
import io.springlets.format.EntityFormat;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@EntityFormat
@Audited
@DiscriminatorValue("BOX")
public class BoxInventoryItem extends InventoryItem {

    @NotNull
    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "BOX_ID")
    @EntityFormat
    private Box box;


    public Box getBox() {
        return this.box;
    }


    public BoxInventoryItem setBox(Box box) {
        this.box = box;
        return this;
    }

    @Override
    public String getBoxBarcode() {
        return box != null ? box.getBarcode() : null;
    }

    @Override
    public String getShelfBarcode() {
        return box != null && box.getShelf() != null ? box.getShelf().getBarcode() : null;
    }

    @Override
    public String getLocation() {
        return box != null ? box.getLocation() : null;
    }
}
