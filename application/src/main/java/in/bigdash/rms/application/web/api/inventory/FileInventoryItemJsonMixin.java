package in.bigdash.rms.application.web.api.inventory;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.api.file.FileDeserializer;
import in.bigdash.rms.model.File;


public abstract class FileInventoryItemJsonMixin {


    @JsonDeserialize(using = FileDeserializer.class)
    private File file;


    public File getFile() {
        return file;
    }


    public void setFile(File file) {
        this.file = file;
    }
}
