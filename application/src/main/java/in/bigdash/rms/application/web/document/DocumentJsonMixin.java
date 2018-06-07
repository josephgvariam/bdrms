package in.bigdash.rms.application.web.document;
import in.bigdash.rms.model.Document;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.model.File;

/**
 * = DocumentJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = Document.class)
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public abstract class DocumentJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = FileDeserializer.class)
    private File file;

    /**
     * TODO Auto-generated method documentation
     *
     * @return File
     */
    public File getFile() {
        return file;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
    }
}
