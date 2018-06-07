package in.bigdash.rms.application.web.document;
import in.bigdash.rms.model.File;
import in.bigdash.rms.service.api.FileService;
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
 * = FileDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = File.class)
@JsonComponent
public class FileDeserializer extends JsonObjectDeserializer<File> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private FileService fileService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param fileService
     * @param conversionService
     */
    @Autowired
    public FileDeserializer(@Lazy FileService fileService, ConversionService conversionService) {
        this.fileService = fileService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return FileService
     */
    public FileService getFileService() {
        return fileService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileService
     */
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
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
     * @return File
     */
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
