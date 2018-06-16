package in.bigdash.rms.application.web.api.shelf;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.service.api.ShelfService;
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
public class ShelfDeserializer extends JsonObjectDeserializer<Shelf> {


    private ShelfService shelfService;


    private ConversionService conversionService;


    @Autowired
    public ShelfDeserializer(@Lazy ShelfService shelfService, ConversionService conversionService) {
        this.shelfService = shelfService;
        this.conversionService = conversionService;
    }


    public ShelfService getShelfService() {
        return shelfService;
    }


    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


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
