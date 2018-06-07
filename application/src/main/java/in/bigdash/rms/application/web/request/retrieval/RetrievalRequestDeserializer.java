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

/**
 * = RetrievalRequestDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = RetrievalRequest.class)
@JsonComponent
public class RetrievalRequestDeserializer extends JsonObjectDeserializer<RetrievalRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RetrievalRequestService retrievalRequestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param retrievalRequestService
     * @param conversionService
     */
    @Autowired
    public RetrievalRequestDeserializer(@Lazy RetrievalRequestService retrievalRequestService, ConversionService conversionService) {
        this.retrievalRequestService = retrievalRequestService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RetrievalRequestService
     */
    public RetrievalRequestService getRetrievalRequestService() {
        return retrievalRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequestService
     */
    public void setRetrievalRequestService(RetrievalRequestService retrievalRequestService) {
        this.retrievalRequestService = retrievalRequestService;
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
     * @return RetrievalRequest
     */
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
