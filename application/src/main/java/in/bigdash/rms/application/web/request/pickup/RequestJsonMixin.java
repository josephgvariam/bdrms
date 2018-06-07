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

/**
 * = RequestJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = Request.class)
public abstract class RequestJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<InventoryItem> inventoryItems;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = UserDeserializer.class)
    private User userAssigned;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = StorageTypeDeserializer.class)
    private StorageType storageType;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = UserDeserializer.class)
    private User userCreated;

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItems
     */
    public void setInventoryItems(Set<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return StorageType
     */
    public StorageType getStorageType() {
        return storageType;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     */
    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return User
     */
    public User getUserAssigned() {
        return userAssigned;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param userAssigned
     */
    public void setUserAssigned(User userAssigned) {
        this.userAssigned = userAssigned;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return User
     */
    public User getUserCreated() {
        return userCreated;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param userCreated
     */
    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }
}
