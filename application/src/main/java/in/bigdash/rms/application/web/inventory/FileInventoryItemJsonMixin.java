package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.document.FileDeserializer;
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
