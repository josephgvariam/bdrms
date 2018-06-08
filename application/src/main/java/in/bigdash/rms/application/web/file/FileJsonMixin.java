package in.bigdash.rms.application.web.file;
import in.bigdash.rms.application.web.box.BoxDeserializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.Document;
import java.util.Set;


@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public abstract class FileJsonMixin {


    @JsonIgnore
    private Set<Document> documents;


    @JsonDeserialize(using = BoxDeserializer.class)
    private Box box;


    public Set<Document> getDocuments() {
        return documents;
    }


    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }


    public Box getBox() {
        return box;
    }


    public void setBox(Box box) {
        this.box = box;
    }
}
