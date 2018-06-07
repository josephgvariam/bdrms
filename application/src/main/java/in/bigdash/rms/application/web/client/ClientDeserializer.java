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

/**
 * = ClientDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Client.class)
@JsonComponent
public class ClientDeserializer extends JsonObjectDeserializer<Client> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ClientService clientService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param clientService
     * @param conversionService
     */
    @Autowired
    public ClientDeserializer(@Lazy ClientService clientService, ConversionService conversionService) {
        this.clientService = clientService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ClientService
     */
    public ClientService getClientService() {
        return clientService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param clientService
     */
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
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
     * @return Client
     */
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
