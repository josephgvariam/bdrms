package in.bigdash.rms.application.web.api.client;
import in.bigdash.rms.model.Client;

import in.bigdash.rms.service.api.ClientService;
import io.springlets.web.NotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@RestController
@RequestMapping(value = "/api/clients/{client}", name = "ClientsItemJsonController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientsItemJsonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ClientService clientService;


    @Autowired
    public ClientsItemJsonController(ClientService clientService) {
        this.clientService = clientService;
    }


    public ClientService getClientService() {
        return clientService;
    }


    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }


    @ModelAttribute
    public Client getClient(@PathVariable("client") Long id) {
        Client client = clientService.findOne(id);
        if (client == null) {
            throw new NotFoundException(String.format("Client with identifier '%s' not found", id));
        }
        return client;
    }


    @GetMapping(name = "show")
    public ResponseEntity<?> show(@ModelAttribute Client client) {
        log.debug("show: {}", client);
        return ResponseEntity.ok(client);
    }


    public static UriComponents showURI(Client client) {
        return MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(ClientsItemJsonController.class).show(client)).buildAndExpand(client.getId()).encode();
    }


    @PutMapping(name = "update")
    public ResponseEntity<?> update(@ModelAttribute Client storedClient, @Valid @RequestBody Client client, BindingResult result) {
        log.debug("update: {}", client);
        if (result.hasErrors()) {
            log.debug("update {} has errors: {}", client, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        client.setId(storedClient.getId());
        Client updatedClient = getClientService().save(client);
        log.debug("update saved: {}", updatedClient);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(name = "delete")
    public ResponseEntity<?> delete(@ModelAttribute Client client) {
        log.debug("delete: {}", client);
        getClientService().delete(client);
        return ResponseEntity.ok().build();
    }
}
