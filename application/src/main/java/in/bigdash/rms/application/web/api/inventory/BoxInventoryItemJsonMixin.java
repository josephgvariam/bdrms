package in.bigdash.rms.application.web.api.inventory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.api.box.BoxDeserializer;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.request.Request;

import java.util.Set;


public abstract class BoxInventoryItemJsonMixin {


    @JsonDeserialize(using = BoxDeserializer.class)
    private Box box;


    @JsonIgnore
    private Set<Request> requests;


    public Box getBox() {
        return box;
    }


    public void setBox(Box box) {
        this.box = box;
    }
}
