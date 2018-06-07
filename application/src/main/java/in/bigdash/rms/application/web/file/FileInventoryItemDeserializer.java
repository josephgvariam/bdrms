package in.bigdash.rms.application.web.file;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import in.bigdash.rms.service.api.FileInventoryItemService;
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
 * = FileInventoryItemDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = FileInventoryItem.class)
@JsonComponent
public class FileInventoryItemDeserializer extends JsonObjectDeserializer<FileInventoryItem> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private FileInventoryItemService fileInventoryItemService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param fileInventoryItemService
     * @param conversionService
     */
    @Autowired
    public FileInventoryItemDeserializer(@Lazy FileInventoryItemService fileInventoryItemService, ConversionService conversionService) {
        this.fileInventoryItemService = fileInventoryItemService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return FileInventoryItemService
     */
    public FileInventoryItemService getFileInventoryItemService() {
        return fileInventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItemService
     */
    public void setFileInventoryItemService(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
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
     * @return FileInventoryItem
     */
    public FileInventoryItem deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        FileInventoryItem fileInventoryItem = fileInventoryItemService.findOne(id);
        if (fileInventoryItem == null) {
            throw new NotFoundException("FileInventoryItem not found");
        }
        return fileInventoryItem;
    }
}
