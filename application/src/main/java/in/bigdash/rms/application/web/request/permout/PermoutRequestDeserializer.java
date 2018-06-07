package in.bigdash.rms.application.web.request.permout;
import in.bigdash.rms.model.request.PermoutRequest;
import in.bigdash.rms.service.api.PermoutRequestService;
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
public class PermoutRequestDeserializer extends JsonObjectDeserializer<PermoutRequest> {


    private PermoutRequestService permoutRequestService;


    private ConversionService conversionService;


    @Autowired
    public PermoutRequestDeserializer(@Lazy PermoutRequestService permoutRequestService, ConversionService conversionService) {
        this.permoutRequestService = permoutRequestService;
        this.conversionService = conversionService;
    }


    public PermoutRequestService getPermoutRequestService() {
        return permoutRequestService;
    }


    public void setPermoutRequestService(PermoutRequestService permoutRequestService) {
        this.permoutRequestService = permoutRequestService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


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
