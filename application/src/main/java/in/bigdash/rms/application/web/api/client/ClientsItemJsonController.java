package in.bigdash.rms.application.web.api.client;
import in.bigdash.rms.model.Client;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.ClientService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

/**
 * = ClientsItemJsonController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Client.class, pathPrefix = "/api", type = ControllerType.ITEM)
@RooJSON
@RestController
@RequestMapping(value = "/api/clients/{client}", name = "ClientsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientsItemJsonController {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ClientService clientService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param clientService
     */
    @Autowired
    public ClientsItemJsonController(ClientService clientService) {
        this.clientService = clientService;
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
     * @param id
     * @return Client
     */
    @ModelAttribute
    public Client getClient(@PathVariable("client") Long id) {
        Client client = clientService.findOne(id);
        if (client == null) {
            throw new NotFoundException(String.format("Client with identifier '%s' not found", id));
        }
        return client;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @return ResponseEntity
     */
    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Client client) {
        return ResponseEntity.ok(client);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @return UriComponents
     */
    public static UriComponents showURI(Client client) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(ClientsItemJsonController.class).show(client)).buildAndExpand(client.getId()).encode();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storedClient
     * @param client
     * @param result
     * @return ResponseEntity
     */
    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Client storedClient, @Valid @RequestBody Client client, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        client.setId(storedClient.getId());
        getClientService().save(client);
        return ResponseEntity.ok().build();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @return ResponseEntity
     */
    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Client client) {
        getClientService().delete(client);
        return ResponseEntity.ok().build();
    }
}
