package in.bigdash.rms.application.web.api.request.pickup;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.api.CustomDateTimeDeserializer;
import in.bigdash.rms.application.web.api.inventory.InventoryItemDeserializer;
import in.bigdash.rms.application.web.api.inventory.InventoryItemsDeserializer;
import in.bigdash.rms.application.web.api.storagetype.StorageTypeDeserializer;
import in.bigdash.rms.application.web.api.user.UserDeserializer;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.model.request.PickupRequest;

import java.util.Calendar;
import java.util.Set;


public abstract class PickupRequestJsonMixin {

    @JsonDeserialize(using = InventoryItemsDeserializer.class)
    private Set<InventoryItem> inventoryItems;


    @JsonDeserialize(using = UserDeserializer.class)
    private User userAssigned;


    @JsonDeserialize(using = StorageTypeDeserializer.class)
    private StorageType storageType;


    @JsonDeserialize(using = UserDeserializer.class)
    private User userCreated;

    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private Calendar pickupDateTime;

    public Calendar getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(Calendar pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

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
