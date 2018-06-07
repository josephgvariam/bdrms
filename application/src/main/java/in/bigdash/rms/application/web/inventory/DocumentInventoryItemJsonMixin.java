package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooJsonMixin;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import in.bigdash.rms.application.web.document.DocumentDeserializer;
import in.bigdash.rms.model.Document;

/**
 * = DocumentInventoryItemJsonMixin
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJsonMixin(entity = DocumentInventoryItem.class)
public abstract class DocumentInventoryItemJsonMixin {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @JsonDeserialize(using = DocumentDeserializer.class)
    private Document document;

    /**
     * TODO Auto-generated method documentation
     *
     * @return Document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param document
     */
    public void setDocument(Document document) {
        this.document = document;
    }
}
