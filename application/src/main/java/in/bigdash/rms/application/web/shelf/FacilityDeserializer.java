package in.bigdash.rms.application.web.shelf;
import in.bigdash.rms.model.Facility;
import in.bigdash.rms.service.api.FacilityService;
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
 * = FacilityDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Facility.class)
@JsonComponent
public class FacilityDeserializer extends JsonObjectDeserializer<Facility> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private FacilityService facilityService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param facilityService
     * @param conversionService
     */
    @Autowired
    public FacilityDeserializer(@Lazy FacilityService facilityService, ConversionService conversionService) {
        this.facilityService = facilityService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return FacilityService
     */
    public FacilityService getFacilityService() {
        return facilityService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facilityService
     */
    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
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
     * @return Facility
     */
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
