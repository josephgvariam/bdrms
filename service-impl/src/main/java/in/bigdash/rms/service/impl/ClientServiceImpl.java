package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.ClientService;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import in.bigdash.rms.repository.client.ClientRepository;
import in.bigdash.rms.service.api.StorageTypeService;
import in.bigdash.rms.service.api.UserService;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private StorageTypeService storageTypeService;


    private ClientRepository clientRepository;


    private UserService userService;


    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, @Lazy StorageTypeService storageTypeService, @Lazy UserService userService) {
        setClientRepository(clientRepository);
        setStorageTypeService(storageTypeService);
        setUserService(userService);
    }


    public ClientRepository getClientRepository() {
        return clientRepository;
    }


    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public StorageTypeService getStorageTypeService() {
        return storageTypeService;
    }


    public void setStorageTypeService(StorageTypeService storageTypeService) {
        this.storageTypeService = storageTypeService;
    }


    public UserService getUserService() {
        return userService;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public Map<String, List<MessageI18n>> validate(Client client) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public Client addToStorageTypes(Client client, Iterable<Long> storageTypesToAdd) {
        List<StorageType> storageTypes = getStorageTypeService().findAll(storageTypesToAdd);
        client.addToStorageTypes(storageTypes);
        return getClientRepository().save(client);
    }


    @Transactional
    public Client addToUsers(Client client, Iterable<Long> usersToAdd) {
        List<User> users = getUserService().findAll(usersToAdd);
        client.addToUsers(users);
        return getClientRepository().save(client);
    }


    @Transactional
    public Client removeFromStorageTypes(Client client, Iterable<Long> storageTypesToRemove) {
        List<StorageType> storageTypes = getStorageTypeService().findAll(storageTypesToRemove);
        client.removeFromStorageTypes(storageTypes);
        return getClientRepository().save(client);
    }


    @Transactional
    public Client removeFromUsers(Client client, Iterable<Long> usersToRemove) {
        List<User> users = getUserService().findAll(usersToRemove);
        client.removeFromUsers(users);
        return getClientRepository().save(client);
    }


    @Transactional
    public Client setStorageTypes(Client client, Iterable<Long> storageTypes) {
        List<StorageType> items = getStorageTypeService().findAll(storageTypes);
        Set<StorageType> currents = client.getStorageTypes();
        Set<StorageType> toRemove = new HashSet<StorageType>();
        for (Iterator<StorageType> iterator = currents.iterator(); iterator.hasNext(); ) {
            StorageType nextStorageType = iterator.next();
            if (items.contains(nextStorageType)) {
                items.remove(nextStorageType);
            } else {
                toRemove.add(nextStorageType);
            }
        }
        client.removeFromStorageTypes(toRemove);
        client.addToStorageTypes(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        client.setVersion(client.getVersion() + 1);
        return getClientRepository().save(client);
    }


    @Transactional
    public Client setUsers(Client client, Iterable<Long> users) {
        List<User> items = getUserService().findAll(users);
        Set<User> currents = client.getUsers();
        Set<User> toRemove = new HashSet<User>();
        for (Iterator<User> iterator = currents.iterator(); iterator.hasNext(); ) {
            User nextUser = iterator.next();
            if (items.contains(nextUser)) {
                items.remove(nextUser);
            } else {
                toRemove.add(nextUser);
            }
        }
        client.removeFromUsers(toRemove);
        client.addToUsers(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        client.setVersion(client.getVersion() + 1);
        return getClientRepository().save(client);
    }


    @Transactional
    public void delete(Client client) {
        // Clear bidirectional many-to-many parent relationship with StorageType
        for (StorageType item : client.getStorageTypes()) {
            item.getClients().remove(client);
        }
        // Clear bidirectional one-to-many parent relationship with User
        for (User item : client.getUsers()) {
            item.setClient(null);
        }
        getClientRepository().delete(client);
    }


    @Transactional
    public List<Client> save(Iterable<Client> entities) {
        return getClientRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Client> toDelete = getClientRepository().findAll(ids);
        getClientRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public Client save(Client entity) {
        return getClientRepository().save(entity);
    }


    public Client findOne(Long id) {
        return getClientRepository().findOne(id);
    }


    public Client findOneForUpdate(Long id) {
        return getClientRepository().findOneDetached(id);
    }


    public List<Client> findAll(Iterable<Long> ids) {
        return getClientRepository().findAll(ids);
    }


    public List<Client> findAll() {
        return getClientRepository().findAll();
    }


    public long count() {
        return getClientRepository().count();
    }


    public Page<Client> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getClientRepository().findAll(globalSearch, pageable);
    }


    public Page<Client> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getClientRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Class<Client> getEntityType() {
        return Client.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
