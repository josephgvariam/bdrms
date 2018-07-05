package in.bigdash.rms.application.web.api.document;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.service.api.DocumentService;
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
public class DocumentDeserializer extends JsonObjectDeserializer<Document> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DocumentService documentService;


    private ConversionService conversionService;


    @Autowired
    public DocumentDeserializer(@Lazy DocumentService documentService, ConversionService conversionService) {
        this.documentService = documentService;
        this.conversionService = conversionService;
    }


    public DocumentService getDocumentService() {
        return documentService;
    }


    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public Document deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        Document document = documentService.findOne(id);
        if (document == null) {
            throw new NotFoundException("Document not found");
        }
        return document;
    }
}
