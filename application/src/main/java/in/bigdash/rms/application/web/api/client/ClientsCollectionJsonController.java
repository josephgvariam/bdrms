package in.bigdash.rms.application.web.api.client;
import in.bigdash.rms.model.Client;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.responses.json.RooJSON;
import in.bigdash.rms.service.api.ClientService;
import io.springlets.data.domain.GlobalSearch;
import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;


@RooController(entity = Client.class, pathPrefix = "/api", type = ControllerType.COLLECTION)
@RooJSON
@RestController
@RequestMapping(value = "/api/clients", name = "ClientsCollectionJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientsCollectionJsonController {


    private ClientService clientService;


    @Autowired
    public ClientsCollectionJsonController(ClientService clientService) {
        this.clientService = clientService;
    }


    public ClientService getClientService() {
        return clientService;
    }


    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping(name = "list")
    public ResponseEntity<Page<Client>> list(GlobalSearch globalSearch, Pageable pageable) {
        Page<Client> clients = getClientService().findAll(globalSearch, pageable);
        return ResponseEntity.ok(clients);
    }


    public static UriComponents listURI() {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(ClientsCollectionJsonController.class).list(null, null)).build().encode();
    }


    @PostMapping(name = "create")
    public ResponseEntity<?> create(@Valid @RequestBody Client client, BindingResult result) {
        if (client.getId() != null || client.getVersion() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        Client newClient = getClientService().save(client);
        UriComponents showURI = ClientsItemJsonController.showURI(newClient);
        return ResponseEntity.created(showURI.toUri()).build();
    }


    @PostMapping(value = "/batch", name = "createBatch")
    public ResponseEntity<?> createBatch(@Valid @RequestBody Collection<Client> clients, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getClientService().save(clients);
        return ResponseEntity.created(listURI().toUri()).build();
    }


    @PutMapping(value = "/batch", name = "updateBatch")
    public ResponseEntity<?> updateBatch(@Valid @RequestBody Collection<Client> clients, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        getClientService().save(clients);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/batch/{ids}", name = "deleteBatch")
    public ResponseEntity<?> deleteBatch(@PathVariable("ids") Collection<Long> ids) {
        getClientService().delete(ids);
        return ResponseEntity.ok().build();
    }
}
