package in.bigdash.rms.application.web.facility;
import in.bigdash.rms.model.Facility;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.bigdash.rms.model.Shelf;
import java.util.Set;


@RooJsonMixin(entity = Facility.class)
public abstract class FacilityJsonMixin {


    @JsonIgnore
    private Set<Shelf> shelves;


    public Set<Shelf> getShelves() {
        return shelves;
    }


    public void setShelves(Set<Shelf> shelves) {
        this.shelves = shelves;
    }
}
