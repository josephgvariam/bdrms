package in.bigdash.rms.application.web.api.inventory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.api.document.DocumentDeserializer;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.model.request.Request;

import java.util.Set;


public abstract class DocumentInventoryItemJsonMixin {


    @JsonDeserialize(using = DocumentDeserializer.class)
    private Document document;

    @JsonIgnore
    private Set<Request> requests;


    public Document getDocument() {
        return document;
    }


    public void setDocument(Document document) {
        this.document = document;
    }
}
