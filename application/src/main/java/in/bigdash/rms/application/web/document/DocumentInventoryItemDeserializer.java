package in.bigdash.rms.application.web.document;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import in.bigdash.rms.service.api.DocumentInventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import io.springlets.web.NotFoundException;
import org.springframework.boot.jackson.JsonComponent;

/**
 * = DocumentInventoryItemDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = DocumentInventoryItem.class)
@JsonComponent
public class DocumentInventoryItemDeserializer extends JsonObjectDeserializer<DocumentInventoryItem> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DocumentInventoryItemService documentInventoryItemService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param documentInventoryItemService
     * @param conversionService
     */
    @Autowired
    public DocumentInventoryItemDeserializer(@Lazy DocumentInventoryItemService documentInventoryItemService, ConversionService conversionService) {
        this.documentInventoryItemService = documentInventoryItemService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DocumentInventoryItemService
     */
    public DocumentInventoryItemService getDocumentInventoryItemService() {
        return documentInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItemService
     */
    public void setDocumentInventoryItemService(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ConversionService
     */
    public ConversionService getConversionService() {
        return conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param conversionService
     */
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param jsonParser
     * @param context
     * @param codec
     * @param tree
     * @return DocumentInventoryItem
     */
    public DocumentInventoryItem deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        DocumentInventoryItem documentInventoryItem = documentInventoryItemService.findOne(id);
        if (documentInventoryItem == null) {
            throw new NotFoundException("DocumentInventoryItem not found");
        }
        return documentInventoryItem;
    }
}
