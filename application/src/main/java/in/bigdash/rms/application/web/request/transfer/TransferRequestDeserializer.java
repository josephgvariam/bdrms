package in.bigdash.rms.application.web.request.transfer;
import in.bigdash.rms.model.request.TransferRequest;
import in.bigdash.rms.service.api.TransferRequestService;
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
 * = TransferRequestDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = TransferRequest.class)
@JsonComponent
public class TransferRequestDeserializer extends JsonObjectDeserializer<TransferRequest> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private TransferRequestService transferRequestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param transferRequestService
     * @param conversionService
     */
    @Autowired
    public TransferRequestDeserializer(@Lazy TransferRequestService transferRequestService, ConversionService conversionService) {
        this.transferRequestService = transferRequestService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return TransferRequestService
     */
    public TransferRequestService getTransferRequestService() {
        return transferRequestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequestService
     */
    public void setTransferRequestService(TransferRequestService transferRequestService) {
        this.transferRequestService = transferRequestService;
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
     * @return TransferRequest
     */
    public TransferRequest deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        TransferRequest transferRequest = transferRequestService.findOne(id);
        if (transferRequest == null) {
            throw new NotFoundException("TransferRequest not found");
        }
        return transferRequest;
    }
}
