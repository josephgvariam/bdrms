package in.bigdash.rms.application.web.client;
import in.bigdash.rms.model.Client;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import java.util.Set;

/**
 * = ClientJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = Client.class)
public abstract class ClientJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<StorageType> storageTypes;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<User> users;

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<StorageType> getStorageTypes() {
        return storageTypes;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageTypes
     */
    public void setStorageTypes(Set<StorageType> storageTypes) {
        this.storageTypes = storageTypes;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
