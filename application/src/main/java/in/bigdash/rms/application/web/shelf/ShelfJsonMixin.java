package in.bigdash.rms.application.web.shelf;
import in.bigdash.rms.model.Shelf;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.Facility;
import java.util.Set;

/**
 * = ShelfJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = Shelf.class)
public abstract class ShelfJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<Box> boxes;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = FacilityDeserializer.class)
    private Facility facility;

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<Box> getBoxes() {
        return boxes;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxes
     */
    public void setBoxes(Set<Box> boxes) {
        this.boxes = boxes;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Facility
     */
    public Facility getFacility() {
        return facility;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     */
    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
