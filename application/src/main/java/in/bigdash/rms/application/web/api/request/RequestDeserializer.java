package in.bigdash.rms.application.web.api.request;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.service.api.RequestService;
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
public class RequestDeserializer extends JsonObjectDeserializer<Request> {


    private RequestService requestService;


    private ConversionService conversionService;


    @Autowired
    public RequestDeserializer(@Lazy RequestService requestService, ConversionService conversionService) {
        this.requestService = requestService;
        this.conversionService = conversionService;
    }


    public RequestService getRequestService() {
        return requestService;
    }


    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public Request deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        Request request = requestService.findOne(id);
        if (request == null) {
            throw new NotFoundException("Request not found");
        }
        return request;
    }
}
