package in.bigdash.rms.application.web.user;
import in.bigdash.rms.model.User;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.client.ClientDeserializer;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.Role;
import in.bigdash.rms.model.request.Request;
import java.util.Set;

/**
 * = UserJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = User.class)
public abstract class UserJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<Request> requestsAssigned;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<Request> requestsCreated;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonIgnore
    private Set<Role> roles;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = ClientDeserializer.class)
    private Client client;

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<Request> getRequestsAssigned() {
        return requestsAssigned;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestsAssigned
     */
    public void setRequestsAssigned(Set<Request> requestsAssigned) {
        this.requestsAssigned = requestsAssigned;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<Request> getRequestsCreated() {
        return requestsCreated;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestsCreated
     */
    public void setRequestsCreated(Set<Request> requestsCreated) {
        this.requestsCreated = requestsCreated;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Set
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Client
     */
    public Client getClient() {
        return client;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
