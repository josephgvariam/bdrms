package in.bigdash.rms.application.web.api.box;
import in.bigdash.rms.application.web.api.shelf.ShelfDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.model.inventory.BoxInventoryItem;

import java.util.Set;



public abstract class BoxJsonMixin {


    @JsonIgnore
    private BoxInventoryItem inventoryItem;

    @JsonIgnore
    private Set<File> files;


    @JsonDeserialize(using = ShelfDeserializer.class)
    private Shelf shelf;


    public Set<File> getFiles() {
        return files;
    }


    public void setFiles(Set<File> files) {
        this.files = files;
    }


    public Shelf getShelf() {
        return shelf;
    }


    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public BoxInventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(BoxInventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }
}
