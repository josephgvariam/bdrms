package in.bigdash.rms.model.inventory;
import in.bigdash.rms.model.Box;
import io.springlets.format.EntityFormat;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Entity;


@Entity
@EntityFormat
public class BoxInventoryItem extends InventoryItem {


    @OneToOne(fetch = FetchType.LAZY)
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
}
