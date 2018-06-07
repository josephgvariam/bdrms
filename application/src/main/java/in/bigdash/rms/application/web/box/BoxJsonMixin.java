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

/**
 * = BoxJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = Box.class)
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public abstract class BoxJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<File> files;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = ShelfDeserializer.class)
    private Shelf shelf;

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<File> getFiles() {
        return files;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param files
     */
    public void setFiles(Set<File> files) {
        this.files = files;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Shelf
     */
    public Shelf getShelf() {
        return shelf;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     */
    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
}
