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

/**
 * = StorageTypeDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = StorageType.class)
@JsonComponent
public class StorageTypeDeserializer extends JsonObjectDeserializer<StorageType> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private StorageTypeService storageTypeService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param storageTypeService
     * @param conversionService
     */
    @Autowired
    public StorageTypeDeserializer(@Lazy StorageTypeService storageTypeService, ConversionService conversionService) {
        this.storageTypeService = storageTypeService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return StorageTypeService
     */
    public StorageTypeService getStorageTypeService() {
        return storageTypeService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageTypeService
     */
    public void setStorageTypeService(StorageTypeService storageTypeService) {
        this.storageTypeService = storageTypeService;
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
     * @return StorageType
     */
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
