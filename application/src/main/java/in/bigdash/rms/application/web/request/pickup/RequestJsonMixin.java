package in.bigdash.rms.application.web.request.pickup;
import in.bigdash.rms.model.request.Request;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.client.StorageTypeDeserializer;
import in.bigdash.rms.application.web.client.UserDeserializer;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.inventory.InventoryItem;
import java.util.Set;


@RooJsonMixin(entity = Request.class)
public abstract class RequestJsonMixin {


    @JsonIgnore
    private Set<InventoryItem> inventoryItems;


    @JsonDeserialize(using = UserDeserializer.class)
    private User userAssigned;


    @JsonDeserialize(using = StorageTypeDeserializer.class)
    private StorageType storageType;


    @JsonDeserialize(using = UserDeserializer.class)
    private User userCreated;


    public Set<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }


    public void setInventoryItems(Set<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }


    public StorageType getStorageType() {
        return storageType;
    }


    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }


    public User getUserAssigned() {
        return userAssigned;
    }


    public void setUserAssigned(User userAssigned) {
        this.userAssigned = userAssigned;
    }


    public User getUserCreated() {
        return userCreated;
    }


    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }
}
