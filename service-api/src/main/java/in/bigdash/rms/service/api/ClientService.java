package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Client;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import org.springframework.roo.addon.layers.service.annotations.RooService;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * = ClientService
 *
 * TODO Auto-generated class documentation
 *
 */
@RooService(entity = Client.class)
public interface ClientService extends EntityResolver<Client, Long>, ValidatorService<Client> {

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Client
     */
    public abstract Client findOne(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     */
    public abstract void delete(Client client);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    public abstract List<Client> save(Iterable<Client> entities);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    public abstract void delete(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return Client
     */
    public abstract Client save(Client entity);

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Client
     */
    public abstract Client findOneForUpdate(Long id);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public abstract List<Client> findAll(Iterable<Long> ids);

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public abstract List<Client> findAll();

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public abstract long count();

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Client> findAll(GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public abstract Page<Client> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param storageTypesToAdd
     * @return Client
     */
    public abstract Client addToStorageTypes(Client client, Iterable<Long> storageTypesToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param storageTypesToRemove
     * @return Client
     */
    public abstract Client removeFromStorageTypes(Client client, Iterable<Long> storageTypesToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param storageTypes
     * @return Client
     */
    public abstract Client setStorageTypes(Client client, Iterable<Long> storageTypes);

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param usersToAdd
     * @return Client
     */
    public abstract Client addToUsers(Client client, Iterable<Long> usersToAdd);

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param usersToRemove
     * @return Client
     */
    public abstract Client removeFromUsers(Client client, Iterable<Long> usersToRemove);

    /**
     * TODO Auto-generated method documentation
     *
     * @param client
     * @param users
     * @return Client
     */
    public abstract Client setUsers(Client client, Iterable<Long> users);
}
