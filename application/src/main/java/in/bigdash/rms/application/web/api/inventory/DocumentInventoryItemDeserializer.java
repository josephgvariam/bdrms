package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import in.bigdash.rms.service.api.DocumentInventoryItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import io.springlets.web.NotFoundException;
import org.springframework.boot.jackson.JsonComponent;


@JsonComponent
public class DocumentInventoryItemDeserializer extends JsonObjectDeserializer<DocumentInventoryItem> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DocumentInventoryItemService documentInventoryItemService;


    private ConversionService conversionService;


    @Autowired
    public DocumentInventoryItemDeserializer(@Lazy DocumentInventoryItemService documentInventoryItemService, ConversionService conversionService) {
        this.documentInventoryItemService = documentInventoryItemService;
        this.conversionService = conversionService;
    }


    public DocumentInventoryItemService getDocumentInventoryItemService() {
        return documentInventoryItemService;
    }


    public void setDocumentInventoryItemService(DocumentInventoryItemService documentInventoryItemService) {
        this.documentInventoryItemService = documentInventoryItemService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


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
