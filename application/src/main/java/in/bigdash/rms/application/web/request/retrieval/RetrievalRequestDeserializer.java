package in.bigdash.rms.application.web.request.retrieval;
import in.bigdash.rms.model.request.RetrievalRequest;
import in.bigdash.rms.service.api.RetrievalRequestService;
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


@RooDeserializer(entity = RetrievalRequest.class)
@JsonComponent
public class RetrievalRequestDeserializer extends JsonObjectDeserializer<RetrievalRequest> {


    private RetrievalRequestService retrievalRequestService;


    private ConversionService conversionService;


    @Autowired
    public RetrievalRequestDeserializer(@Lazy RetrievalRequestService retrievalRequestService, ConversionService conversionService) {
        this.retrievalRequestService = retrievalRequestService;
        this.conversionService = conversionService;
    }


    public RetrievalRequestService getRetrievalRequestService() {
        return retrievalRequestService;
    }


    public void setRetrievalRequestService(RetrievalRequestService retrievalRequestService) {
        this.retrievalRequestService = retrievalRequestService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public RetrievalRequest deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        RetrievalRequest retrievalRequest = retrievalRequestService.findOne(id);
        if (retrievalRequest == null) {
            throw new NotFoundException("RetrievalRequest not found");
        }
        return retrievalRequest;
    }
}
