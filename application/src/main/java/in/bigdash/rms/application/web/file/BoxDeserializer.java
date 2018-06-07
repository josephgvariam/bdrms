package in.bigdash.rms.application.web.file;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.service.api.BoxService;
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
 * = BoxDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Box.class)
@JsonComponent
public class BoxDeserializer extends JsonObjectDeserializer<Box> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private BoxService boxService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param boxService
     * @param conversionService
     */
    @Autowired
    public BoxDeserializer(@Lazy BoxService boxService, ConversionService conversionService) {
        this.boxService = boxService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return BoxService
     */
    public BoxService getBoxService() {
        return boxService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxService
     */
    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
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
     * @return Box
     */
    public Box deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        Box box = boxService.findOne(id);
        if (box == null) {
            throw new NotFoundException("Box not found");
        }
        return box;
    }
}
