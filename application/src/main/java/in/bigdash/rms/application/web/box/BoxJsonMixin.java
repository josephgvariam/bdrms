package in.bigdash.rms.application.web.box;
import in.bigdash.rms.model.Box;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.Shelf;
import java.util.Set;


@RooJsonMixin(entity = Box.class)
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public abstract class BoxJsonMixin {


    @JsonIgnore
    private Set<File> files;


    @JsonDeserialize(using = ShelfDeserializer.class)
    private Shelf shelf;


    public Set<File> getFiles() {
        return files;
    }


    public void setFiles(Set<File> files) {
        this.files = files;
    }


    public Shelf getShelf() {
        return shelf;
    }


    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
}
