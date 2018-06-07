package in.bigdash.rms.application.web.user;
import in.bigdash.rms.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.client.ClientDeserializer;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.Role;
import in.bigdash.rms.model.request.Request;
import java.util.Set;


public abstract class UserJsonMixin {


    @JsonIgnore
    private Set<Request> requestsAssigned;


    @JsonIgnore
    private Set<Request> requestsCreated;


    @JsonIgnore
    private Set<Role> roles;


    @JsonDeserialize(using = ClientDeserializer.class)
    private Client client;


    public Set<Request> getRequestsAssigned() {
        return requestsAssigned;
    }


    public void setRequestsAssigned(Set<Request> requestsAssigned) {
        this.requestsAssigned = requestsAssigned;
    }


    public Set<Request> getRequestsCreated() {
        return requestsCreated;
    }


    public void setRequestsCreated(Set<Request> requestsCreated) {
        this.requestsCreated = requestsCreated;
    }


    public Set<Role> getRoles() {
        return roles;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public Client getClient() {
        return client;
    }


    public void setClient(Client client) {
        this.client = client;
    }
}
