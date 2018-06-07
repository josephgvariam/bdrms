package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.file.BoxDeserializer;
import in.bigdash.rms.model.Box;


@RooJsonMixin(entity = BoxInventoryItem.class)
public abstract class BoxInventoryItemJsonMixin {


    @JsonDeserialize(using = BoxDeserializer.class)
    private Box box;


    public Box getBox() {
        return box;
    }


    public void setBox(Box box) {
        this.box = box;
    }
}
