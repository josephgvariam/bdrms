package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.ClientService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import in.bigdash.rms.repository.ClientRepository;
import in.bigdash.rms.service.api.StorageTypeService;
import in.bigdash.rms.service.api.UserService;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * = ClientServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = ClientService.class)
@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private StorageTypeService storageTypeService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ClientRepository clientRepository;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private UserService userService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param clientRepository
     * @param storageTypeService
     * @param userService
     */
    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, @Lazy StorageTypeService storageTypeService, @Lazy UserService userService) {
        setClientRepository(clientRepository);
        setStorageTypeService(storageTypeService);
        setUserService(userService);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ClientRepository
     */
    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param clientRepository
     */
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return StorageTypeService
     */
    public StorageTypeService getStorageTypeService() {
        return storageTypeService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageTypeService
     */
    public void setStorageTypeService(StorageTypeService storageTypeService) {
        this.storageTypeService = storageTypeService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return UserService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param userService
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(Client client) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param storageTypesToAdd
     * @return Client
     */
    @Transactional
    public Client addToStorageTypes(Client client, Iterable<Long> storageTypesToAdd) {
        List<StorageType> storageTypes = getStorageTypeService().findAll(storageTypesToAdd);
        client.addToStorageTypes(storageTypes);
        return getClientRepository().save(client);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param usersToAdd
     * @return Client
     */
    @Transactional
    public Client addToUsers(Client client, Iterable<Long> usersToAdd) {
        List<User> users = getUserService().findAll(usersToAdd);
        client.addToUsers(users);
        return getClientRepository().save(client);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param storageTypesToRemove
     * @return Client
     */
    @Transactional
    public Client removeFromStorageTypes(Client client, Iterable<Long> storageTypesToRemove) {
        List<StorageType> storageTypes = getStorageTypeService().findAll(storageTypesToRemove);
        client.removeFromStorageTypes(storageTypes);
        return getClientRepository().save(client);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param usersToRemove
     * @return Client
     */
    @Transactional
    public Client removeFromUsers(Client client, Iterable<Long> usersToRemove) {
        List<User> users = getUserService().findAll(usersToRemove);
        client.removeFromUsers(users);
        return getClientRepository().save(client);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param storageTypes
     * @return Client
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param users
     * @return Client
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<Client> save(Iterable<Client> entities) {
        return getClientRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Client> toDelete = getClientRepository().findAll(ids);
        getClientRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return Client
     */
    @Transactional
    public Client save(Client entity) {
        return getClientRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Client
     */
    public Client findOne(Long id) {
        return getClientRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Client
     */
    public Client findOneForUpdate(Long id) {
        return getClientRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<Client> findAll(Iterable<Long> ids) {
        return getClientRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<Client> findAll() {
        return getClientRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getClientRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Client> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getClientRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Client> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getClientRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<Client> getEntityType() {
        return Client.class;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<Long> getIdType() {
        return Long.class;
    }
}
