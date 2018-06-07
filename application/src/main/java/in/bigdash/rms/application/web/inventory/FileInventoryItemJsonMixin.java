package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.document.FileDeserializer;
import in.bigdash.rms.model.File;


@RooJsonMixin(entity = FileInventoryItem.class)
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
