package in.bigdash.rms.application.web.api.inventory;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.api.box.BoxDeserializer;
import in.bigdash.rms.model.Box;


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
