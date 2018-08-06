package in.bigdash.rms.application.web.api.request.insertion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.inventory.InsertInventoryItem;
import in.bigdash.rms.model.request.InsertionRequest;

import java.util.Set;


public abstract class InsertionRequestJsonMixin {
    @JsonIgnore
    private Set<InsertInventoryItem> nsertInventoryItems;
}
