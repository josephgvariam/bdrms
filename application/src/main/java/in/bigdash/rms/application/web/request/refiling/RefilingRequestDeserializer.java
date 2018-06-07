package in.bigdash.rms.application.web.request.refiling;
import in.bigdash.rms.model.request.RefilingRequest;
import in.bigdash.rms.service.api.RefilingRequestService;
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
 * = RefilingRequestDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = RefilingRequest.class)
@JsonComponent
public class RefilingRequestDeserializer extends JsonObjectDeserializer<RefilingRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RefilingRequestService refilingRequestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param refilingRequestService
     * @param conversionService
     */
    @Autowired
    public RefilingRequestDeserializer(@Lazy RefilingRequestService refilingRequestService, ConversionService conversionService) {
        this.refilingRequestService = refilingRequestService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RefilingRequestService
     */
    public RefilingRequestService getRefilingRequestService() {
        return refilingRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param refilingRequestService
     */
    public void setRefilingRequestService(RefilingRequestService refilingRequestService) {
        this.refilingRequestService = refilingRequestService;
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
     * @return RefilingRequest
     */
    public RefilingRequest deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        RefilingRequest refilingRequest = refilingRequestService.findOne(id);
        if (refilingRequest == null) {
            throw new NotFoundException("RefilingRequest not found");
        }
        return refilingRequest;
    }
}
