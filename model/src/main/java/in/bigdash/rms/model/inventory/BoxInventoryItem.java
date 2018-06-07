package in.bigdash.rms.model.inventory;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import in.bigdash.rms.model.Box;
import io.springlets.format.EntityFormat;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Entity;

/**
 * = BoxInventoryItem
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity
@Entity
@EntityFormat
public class BoxInventoryItem extends InventoryItem {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOX_ID")
    @EntityFormat
    private Box box;

    /**
     * Gets box value
     *
     * @return Box
     */
    public Box getBox() {
        return this.box;
    }

    /**
     * Sets box value
     *
     * @param box
     * @return BoxInventoryItem
     */
    public BoxInventoryItem setBox(Box box) {
        this.box = box;
        return this;
    }
}
