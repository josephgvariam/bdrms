package in.bigdash.rms.application.web.api.file;
import in.bigdash.rms.application.web.api.box.BoxDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.model.inventory.FileInventoryItem;

import java.util.Set;


public abstract class FileJsonMixin {

    @JsonIgnore
    private FileInventoryItem inventoryItem;

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

    public FileInventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(FileInventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }
}
