package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.document.DocumentDeserializer;
import in.bigdash.rms.model.Document;


@RooJsonMixin(entity = DocumentInventoryItem.class)
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
