package in.bigdash.rms.application.web.shelf;
import in.bigdash.rms.model.Shelf;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.Facility;
import java.util.Set;


@RooJsonMixin(entity = Shelf.class)
public abstract class ShelfJsonMixin {


    @JsonIgnore
    private Set<Box> boxes;


    @JsonDeserialize(using = FacilityDeserializer.class)
    private Facility facility;


    public Set<Box> getBoxes() {
        return boxes;
    }


    public void setBoxes(Set<Box> boxes) {
        this.boxes = boxes;
    }


    public Facility getFacility() {
        return facility;
    }


    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
