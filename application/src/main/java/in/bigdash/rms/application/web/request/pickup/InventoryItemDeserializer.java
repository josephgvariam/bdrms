package in.bigdash.rms.application.web.request.pickup;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.service.api.InventoryItemService;
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
 * = InventoryItemDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = InventoryItem.class)
@JsonComponent
public class InventoryItemDeserializer extends JsonObjectDeserializer<InventoryItem> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private InventoryItemService inventoryItemService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param inventoryItemService
     * @param conversionService
     */
    @Autowired
    public InventoryItemDeserializer(@Lazy InventoryItemService inventoryItemService, ConversionService conversionService) {
        this.inventoryItemService = inventoryItemService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return InventoryItemService
     */
    public InventoryItemService getInventoryItemService() {
        return inventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItemService
     */
    public void setInventoryItemService(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
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
     * @return InventoryItem
     */
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
