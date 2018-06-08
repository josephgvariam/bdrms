package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.service.api.InventoryItemService;
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
public class InventoryItemDeserializer extends JsonObjectDeserializer<InventoryItem> {


    private InventoryItemService inventoryItemService;


    private ConversionService conversionService;


    @Autowired
    public InventoryItemDeserializer(@Lazy InventoryItemService inventoryItemService, ConversionService conversionService) {
        this.inventoryItemService = inventoryItemService;
        this.conversionService = conversionService;
    }


    public InventoryItemService getInventoryItemService() {
        return inventoryItemService;
    }


    public void setInventoryItemService(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public InventoryItem deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        InventoryItem inventoryItem = inventoryItemService.findOne(id);
        if (inventoryItem == null) {
            throw new NotFoundException("InventoryItem not found");
        }
        return inventoryItem;
    }
}
