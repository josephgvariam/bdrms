package in.bigdash.rms.application.web.api.request.destruction;
import in.bigdash.rms.model.request.DestructionRequest;
import in.bigdash.rms.service.api.DestructionRequestService;
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
public class DestructionRequestDeserializer extends JsonObjectDeserializer<DestructionRequest> {


    private DestructionRequestService destructionRequestService;


    private ConversionService conversionService;


    @Autowired
    public DestructionRequestDeserializer(@Lazy DestructionRequestService destructionRequestService, ConversionService conversionService) {
        this.destructionRequestService = destructionRequestService;
        this.conversionService = conversionService;
    }


    public DestructionRequestService getDestructionRequestService() {
        return destructionRequestService;
    }


    public void setDestructionRequestService(DestructionRequestService destructionRequestService) {
        this.destructionRequestService = destructionRequestService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public DestructionRequest deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        DestructionRequest destructionRequest = destructionRequestService.findOne(id);
        if (destructionRequest == null) {
            throw new NotFoundException("DestructionRequest not found");
        }
        return destructionRequest;
    }
}
