package in.bigdash.rms.application.web.facility;
import in.bigdash.rms.model.Facility;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.bigdash.rms.model.Shelf;
import java.util.Set;

/**
 * = FacilityJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = Facility.class)
public abstract class FacilityJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<Shelf> shelves;

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<Shelf> getShelves() {
        return shelves;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelves
     */
    public void setShelves(Set<Shelf> shelves) {
        this.shelves = shelves;
    }
}
