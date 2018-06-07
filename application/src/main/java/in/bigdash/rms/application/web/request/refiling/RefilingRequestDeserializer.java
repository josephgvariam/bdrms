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


@RooDeserializer(entity = RefilingRequest.class)
@JsonComponent
public class RefilingRequestDeserializer extends JsonObjectDeserializer<RefilingRequest> {


    private RefilingRequestService refilingRequestService;


    private ConversionService conversionService;


    @Autowired
    public RefilingRequestDeserializer(@Lazy RefilingRequestService refilingRequestService, ConversionService conversionService) {
        this.refilingRequestService = refilingRequestService;
        this.conversionService = conversionService;
    }


    public RefilingRequestService getRefilingRequestService() {
        return refilingRequestService;
    }


    public void setRefilingRequestService(RefilingRequestService refilingRequestService) {
        this.refilingRequestService = refilingRequestService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


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
