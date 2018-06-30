package in.bigdash.rms.application.web.api.shelf;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.service.api.ShelfService;
import org.apache.commons.lang3.StringUtils;
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


    public Shelf deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode node) {

        Shelf shelf = null;

        Long id = getLong(node);

        if(id != null) {
            shelf = shelfService.findOne(id);
        }else{
            String barcode = node.get("barcode").asText();
            shelf = shelfService.findByBarcode(barcode);
        }

        if (shelf == null) {
            throw new NotFoundException("Shelf not found");
        }
        return shelf;
    }

    private Long getLong(JsonNode node){

        if(node == null){
            return null;
        }

        String idText = node.asText();

        if(StringUtils.isBlank(idText) || idText.equals("null")){
            return null;
        }else{
            return Long.parseLong(idText);
        }
    }
}
