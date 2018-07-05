package in.bigdash.rms.application.web.api.facility;
import in.bigdash.rms.model.Facility;
import in.bigdash.rms.service.api.FacilityService;
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
public class FacilityDeserializer extends JsonObjectDeserializer<Facility> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private FacilityService facilityService;


    private ConversionService conversionService;


    @Autowired
    public FacilityDeserializer(@Lazy FacilityService facilityService, ConversionService conversionService) {
        this.facilityService = facilityService;
        this.conversionService = conversionService;
    }


    public FacilityService getFacilityService() {
        return facilityService;
    }


    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public Facility deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        Facility facility = facilityService.findOne(id);
        if (facility == null) {
            throw new NotFoundException("Facility not found");
        }
        return facility;
    }
}
