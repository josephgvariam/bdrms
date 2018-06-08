package in.bigdash.rms.application.web.inventory;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import in.bigdash.rms.service.api.FileInventoryItemService;
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
public class FileInventoryItemDeserializer extends JsonObjectDeserializer<FileInventoryItem> {


    private FileInventoryItemService fileInventoryItemService;


    private ConversionService conversionService;


    @Autowired
    public FileInventoryItemDeserializer(@Lazy FileInventoryItemService fileInventoryItemService, ConversionService conversionService) {
        this.fileInventoryItemService = fileInventoryItemService;
        this.conversionService = conversionService;
    }


    public FileInventoryItemService getFileInventoryItemService() {
        return fileInventoryItemService;
    }


    public void setFileInventoryItemService(FileInventoryItemService fileInventoryItemService) {
        this.fileInventoryItemService = fileInventoryItemService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


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
