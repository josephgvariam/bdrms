package in.bigdash.rms.application.web.api.request.transfer;
import in.bigdash.rms.model.request.TransferRequest;
import in.bigdash.rms.service.api.TransferRequestService;
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
public class TransferRequestDeserializer extends JsonObjectDeserializer<TransferRequest> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private TransferRequestService transferRequestService;


    private ConversionService conversionService;


    @Autowired
    public TransferRequestDeserializer(@Lazy TransferRequestService transferRequestService, ConversionService conversionService) {
        this.transferRequestService = transferRequestService;
        this.conversionService = conversionService;
    }


    public TransferRequestService getTransferRequestService() {
        return transferRequestService;
    }


    public void setTransferRequestService(TransferRequestService transferRequestService) {
        this.transferRequestService = transferRequestService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


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
