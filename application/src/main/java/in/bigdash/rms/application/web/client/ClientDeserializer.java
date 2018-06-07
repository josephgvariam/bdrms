package in.bigdash.rms.application.web.client;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.service.api.ClientService;
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


@RooDeserializer(entity = Client.class)
@JsonComponent
public class ClientDeserializer extends JsonObjectDeserializer<Client> {


    private ClientService clientService;


    private ConversionService conversionService;


    @Autowired
    public ClientDeserializer(@Lazy ClientService clientService, ConversionService conversionService) {
        this.clientService = clientService;
        this.conversionService = conversionService;
    }


    public ClientService getClientService() {
        return clientService;
    }


    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public Client deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        Client client = clientService.findOne(id);
        if (client == null) {
            throw new NotFoundException("Client not found");
        }
        return client;
    }
}
