package in.bigdash.rms.application.web.api.document;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.bigdash.rms.application.web.api.file.FileDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;


public abstract class DocumentJsonMixin {

    @JsonIgnore
    private DocumentInventoryItem inventoryItem;


    @JsonDeserialize(using = FileDeserializer.class)
    private File file;


    public File getFile() {
        return file;
    }


    public void setFile(File file) {
        this.file = file;
    }

    public DocumentInventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(DocumentInventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }
}
