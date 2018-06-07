package in.bigdash.rms.application.web.document;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.service.api.DocumentService;
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
 * = DocumentDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Document.class)
@JsonComponent
public class DocumentDeserializer extends JsonObjectDeserializer<Document> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DocumentService documentService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param documentService
     * @param conversionService
     */
    @Autowired
    public DocumentDeserializer(@Lazy DocumentService documentService, ConversionService conversionService) {
        this.documentService = documentService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DocumentService
     */
    public DocumentService getDocumentService() {
        return documentService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentService
     */
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
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
     * @return Document
     */
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
