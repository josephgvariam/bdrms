package in.bigdash.rms.application.web.file;
import in.bigdash.rms.model.File;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.Document;
import java.util.Set;

/**
 * = FileJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = File.class)
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public abstract class FileJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<Document> documents;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = BoxDeserializer.class)
    private Box box;

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<Document> getDocuments() {
        return documents;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documents
     */
    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

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
