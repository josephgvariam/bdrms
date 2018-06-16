package in.bigdash.rms.application.web.api.inventory;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.api.document.DocumentDeserializer;
import in.bigdash.rms.model.Document;


public abstract class DocumentInventoryItemJsonMixin {


    @JsonDeserialize(using = DocumentDeserializer.class)
    private Document document;


    public Document getDocument() {
        return document;
    }


    public void setDocument(Document document) {
        this.document = document;
    }
}
