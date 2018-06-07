package in.bigdash.rms.application.web.request.permout;
import in.bigdash.rms.model.request.PermoutRequest;
import in.bigdash.rms.service.api.PermoutRequestService;
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
 * = PermoutRequestDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = PermoutRequest.class)
@JsonComponent
public class PermoutRequestDeserializer extends JsonObjectDeserializer<PermoutRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private PermoutRequestService permoutRequestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param permoutRequestService
     * @param conversionService
     */
    @Autowired
    public PermoutRequestDeserializer(@Lazy PermoutRequestService permoutRequestService, ConversionService conversionService) {
        this.permoutRequestService = permoutRequestService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return PermoutRequestService
     */
    public PermoutRequestService getPermoutRequestService() {
        return permoutRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param permoutRequestService
     */
    public void setPermoutRequestService(PermoutRequestService permoutRequestService) {
        this.permoutRequestService = permoutRequestService;
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
     * @return PermoutRequest
     */
    public PermoutRequest deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        PermoutRequest permoutRequest = permoutRequestService.findOne(id);
        if (permoutRequest == null) {
            throw new NotFoundException("PermoutRequest not found");
        }
        return permoutRequest;
    }
}
