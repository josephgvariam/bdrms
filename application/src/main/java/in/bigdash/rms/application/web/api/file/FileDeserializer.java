package in.bigdash.rms.application.web.api.file;
import in.bigdash.rms.model.File;
import in.bigdash.rms.service.api.FileService;
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
public class FileDeserializer extends JsonObjectDeserializer<File> {


    private FileService fileService;


    private ConversionService conversionService;


    @Autowired
    public FileDeserializer(@Lazy FileService fileService, ConversionService conversionService) {
        this.fileService = fileService;
        this.conversionService = conversionService;
    }


    public FileService getFileService() {
        return fileService;
    }


    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public File deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        File file = fileService.findOne(id);
        if (file == null) {
            throw new NotFoundException("File not found");
        }
        return file;
    }
}
