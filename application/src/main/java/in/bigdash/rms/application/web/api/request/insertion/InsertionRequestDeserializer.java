package in.bigdash.rms.application.web.api.request.insertion;
import in.bigdash.rms.model.request.InsertionRequest;
import in.bigdash.rms.service.api.InsertionRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class InsertionRequestDeserializer extends JsonObjectDeserializer<InsertionRequest> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private InsertionRequestService insertionRequestService;


    private ConversionService conversionService;


    @Autowired
    public InsertionRequestDeserializer(@Lazy InsertionRequestService insertionRequestService, ConversionService conversionService) {
        this.insertionRequestService = insertionRequestService;
        this.conversionService = conversionService;
    }


    public InsertionRequestService getInsertionRequestService() {
        return insertionRequestService;
    }


    public void setInsertionRequestService(InsertionRequestService insertionRequestService) {
        this.insertionRequestService = insertionRequestService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


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
