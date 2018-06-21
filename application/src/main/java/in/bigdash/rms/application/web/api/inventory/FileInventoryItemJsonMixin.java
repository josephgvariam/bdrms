package in.bigdash.rms.application.web.api.inventory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.api.file.FileDeserializer;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.request.Request;

import java.util.Set;


public abstract class FileInventoryItemJsonMixin {


    @JsonDeserialize(using = FileDeserializer.class)
    private File file;


    @JsonIgnore
    private Set<Request> requests;


    public File getFile() {
        return file;
    }


    public void setFile(File file) {
        this.file = file;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }
}
