package in.bigdash.rms.application.web.box;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import in.bigdash.rms.service.api.BoxInventoryItemService;
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
 * = BoxInventoryItemDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = BoxInventoryItem.class)
@JsonComponent
public class BoxInventoryItemDeserializer extends JsonObjectDeserializer<BoxInventoryItem> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private BoxInventoryItemService boxInventoryItemService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param boxInventoryItemService
     * @param conversionService
     */
    @Autowired
    public BoxInventoryItemDeserializer(@Lazy BoxInventoryItemService boxInventoryItemService, ConversionService conversionService) {
        this.boxInventoryItemService = boxInventoryItemService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return BoxInventoryItemService
     */
    public BoxInventoryItemService getBoxInventoryItemService() {
        return boxInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxInventoryItemService
     */
    public void setBoxInventoryItemService(BoxInventoryItemService boxInventoryItemService) {
        this.boxInventoryItemService = boxInventoryItemService;
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
     * @return BoxInventoryItem
     */
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
