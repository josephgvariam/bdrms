package in.bigdash.rms.application.web.client;
import in.bigdash.rms.model.Client;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import java.util.Set;


@RooJsonMixin(entity = Client.class)
public abstract class ClientJsonMixin {


    @JsonIgnore
    private Set<StorageType> storageTypes;


    @JsonIgnore
    private Set<User> users;


    public Set<StorageType> getStorageTypes() {
        return storageTypes;
    }


    public void setStorageTypes(Set<StorageType> storageTypes) {
        this.storageTypes = storageTypes;
    }


    public Set<User> getUsers() {
        return users;
    }


    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
