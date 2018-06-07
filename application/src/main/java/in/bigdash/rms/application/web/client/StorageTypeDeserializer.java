package in.bigdash.rms.application.web.client;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.service.api.StorageTypeService;
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


@RooDeserializer(entity = StorageType.class)
@JsonComponent
public class StorageTypeDeserializer extends JsonObjectDeserializer<StorageType> {


    private StorageTypeService storageTypeService;


    private ConversionService conversionService;


    @Autowired
    public StorageTypeDeserializer(@Lazy StorageTypeService storageTypeService, ConversionService conversionService) {
        this.storageTypeService = storageTypeService;
        this.conversionService = conversionService;
    }


    public StorageTypeService getStorageTypeService() {
        return storageTypeService;
    }


    public void setStorageTypeService(StorageTypeService storageTypeService) {
        this.storageTypeService = storageTypeService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public StorageType deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        StorageType storageType = storageTypeService.findOne(id);
        if (storageType == null) {
            throw new NotFoundException("StorageType not found");
        }
        return storageType;
    }
}
