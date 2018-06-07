package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.document.FileDeserializer;
import in.bigdash.rms.model.File;

/**
 * = FileInventoryItemJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = FileInventoryItem.class)
public abstract class FileInventoryItemJsonMixin {

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
