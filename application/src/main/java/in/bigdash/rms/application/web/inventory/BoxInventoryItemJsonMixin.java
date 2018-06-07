package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.file.BoxDeserializer;
import in.bigdash.rms.model.Box;

/**
 * = BoxInventoryItemJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = BoxInventoryItem.class)
public abstract class BoxInventoryItemJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = BoxDeserializer.class)
    private Box box;

    /**
     * TODO Auto-generated method documentation
     *
     * @return Box
     */
    public Box getBox() {
        return box;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     */
    public void setBox(Box box) {
        this.box = box;
    }
}
