package in.bigdash.rms.application.web.request.destruction;
import in.bigdash.rms.model.request.DestructionRequest;
import in.bigdash.rms.service.api.DestructionRequestService;
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
 * = DestructionRequestDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = DestructionRequest.class)
@JsonComponent
public class DestructionRequestDeserializer extends JsonObjectDeserializer<DestructionRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DestructionRequestService destructionRequestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param destructionRequestService
     * @param conversionService
     */
    @Autowired
    public DestructionRequestDeserializer(@Lazy DestructionRequestService destructionRequestService, ConversionService conversionService) {
        this.destructionRequestService = destructionRequestService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DestructionRequestService
     */
    public DestructionRequestService getDestructionRequestService() {
        return destructionRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequestService
     */
    public void setDestructionRequestService(DestructionRequestService destructionRequestService) {
        this.destructionRequestService = destructionRequestService;
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
     * @return DestructionRequest
     */
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
