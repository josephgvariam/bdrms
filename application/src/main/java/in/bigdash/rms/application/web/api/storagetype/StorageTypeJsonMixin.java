package in.bigdash.rms.application.web.api.storagetype;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.request.Request;

import java.util.Set;


public abstract class StorageTypeJsonMixin {


    @JsonIgnore
    private Set<Request> requests;


    public Set<Request> getRequests() {
        return requests;
    }


    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }
}
