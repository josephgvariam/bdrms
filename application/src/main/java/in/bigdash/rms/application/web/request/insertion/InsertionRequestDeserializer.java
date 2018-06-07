package in.bigdash.rms.application.web.request.insertion;
import in.bigdash.rms.model.request.InsertionRequest;
import in.bigdash.rms.service.api.InsertionRequestService;
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
 * = InsertionRequestDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = InsertionRequest.class)
@JsonComponent
public class InsertionRequestDeserializer extends JsonObjectDeserializer<InsertionRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private InsertionRequestService insertionRequestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param insertionRequestService
     * @param conversionService
     */
    @Autowired
    public InsertionRequestDeserializer(@Lazy InsertionRequestService insertionRequestService, ConversionService conversionService) {
        this.insertionRequestService = insertionRequestService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return InsertionRequestService
     */
    public InsertionRequestService getInsertionRequestService() {
        return insertionRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequestService
     */
    public void setInsertionRequestService(InsertionRequestService insertionRequestService) {
        this.insertionRequestService = insertionRequestService;
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
     * @return InsertionRequest
     */
    public InsertionRequest deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        InsertionRequest insertionRequest = insertionRequestService.findOne(id);
        if (insertionRequest == null) {
            throw new NotFoundException("InsertionRequest not found");
        }
        return insertionRequest;
    }
}
