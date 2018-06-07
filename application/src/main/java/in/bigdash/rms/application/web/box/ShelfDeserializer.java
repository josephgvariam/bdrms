package in.bigdash.rms.application.web.box;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.service.api.ShelfService;
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
 * = ShelfDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Shelf.class)
@JsonComponent
public class ShelfDeserializer extends JsonObjectDeserializer<Shelf> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ShelfService shelfService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param shelfService
     * @param conversionService
     */
    @Autowired
    public ShelfDeserializer(@Lazy ShelfService shelfService, ConversionService conversionService) {
        this.shelfService = shelfService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ShelfService
     */
    public ShelfService getShelfService() {
        return shelfService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelfService
     */
    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
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
     * @return Shelf
     */
    public Shelf deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        Shelf shelf = shelfService.findOne(id);
        if (shelf == null) {
            throw new NotFoundException("Shelf not found");
        }
        return shelf;
    }
}
