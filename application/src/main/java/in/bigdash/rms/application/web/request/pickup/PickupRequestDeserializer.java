package in.bigdash.rms.application.web.request.pickup;
import in.bigdash.rms.model.request.PickupRequest;
import in.bigdash.rms.service.api.PickupRequestService;
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
public class PickupRequestDeserializer extends JsonObjectDeserializer<PickupRequest> {


    private PickupRequestService pickupRequestService;


    private ConversionService conversionService;


    @Autowired
    public PickupRequestDeserializer(@Lazy PickupRequestService pickupRequestService, ConversionService conversionService) {
        this.pickupRequestService = pickupRequestService;
        this.conversionService = conversionService;
    }


    public PickupRequestService getPickupRequestService() {
        return pickupRequestService;
    }


    public void setPickupRequestService(PickupRequestService pickupRequestService) {
        this.pickupRequestService = pickupRequestService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public PickupRequest deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        PickupRequest pickupRequest = pickupRequestService.findOne(id);
        if (pickupRequest == null) {
            throw new NotFoundException("PickupRequest not found");
        }
        return pickupRequest;
    }
}
