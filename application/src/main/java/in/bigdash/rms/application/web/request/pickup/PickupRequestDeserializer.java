package in.bigdash.rms.application.web.request.pickup;
import in.bigdash.rms.model.request.PickupRequest;
import in.bigdash.rms.service.api.PickupRequestService;
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
 * = PickupRequestDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = PickupRequest.class)
@JsonComponent
public class PickupRequestDeserializer extends JsonObjectDeserializer<PickupRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private PickupRequestService pickupRequestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param pickupRequestService
     * @param conversionService
     */
    @Autowired
    public PickupRequestDeserializer(@Lazy PickupRequestService pickupRequestService, ConversionService conversionService) {
        this.pickupRequestService = pickupRequestService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return PickupRequestService
     */
    public PickupRequestService getPickupRequestService() {
        return pickupRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param pickupRequestService
     */
    public void setPickupRequestService(PickupRequestService pickupRequestService) {
        this.pickupRequestService = pickupRequestService;
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
     * @return PickupRequest
     */
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
