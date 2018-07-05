package in.bigdash.rms.application.web.api.inventory;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import in.bigdash.rms.service.api.BoxInventoryItemService;
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
public class BoxInventoryItemDeserializer extends JsonObjectDeserializer<BoxInventoryItem> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BoxInventoryItemService boxInventoryItemService;


    private ConversionService conversionService;


    @Autowired
    public BoxInventoryItemDeserializer(@Lazy BoxInventoryItemService boxInventoryItemService, ConversionService conversionService) {
        this.boxInventoryItemService = boxInventoryItemService;
        this.conversionService = conversionService;
    }


    public BoxInventoryItemService getBoxInventoryItemService() {
        return boxInventoryItemService;
    }


    public void setBoxInventoryItemService(BoxInventoryItemService boxInventoryItemService) {
        this.boxInventoryItemService = boxInventoryItemService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public BoxInventoryItem deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        BoxInventoryItem boxInventoryItem = boxInventoryItemService.findOne(id);
        if (boxInventoryItem == null) {
            throw new NotFoundException("BoxInventoryItem not found");
        }
        return boxInventoryItem;
    }
}
