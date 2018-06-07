package in.bigdash.rms.service.api;
import in.bigdash.rms.model.Client;
import io.springlets.data.web.validation.ValidatorService;
import io.springlets.format.EntityResolver;
import io.springlets.data.domain.GlobalSearch;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClientService extends EntityResolver<Client, Long>, ValidatorService<Client> {


    public abstract Client findOne(Long id);


    public abstract void delete(Client client);


    public abstract List<Client> save(Iterable<Client> entities);


    public abstract void delete(Iterable<Long> ids);


    public abstract Client save(Client entity);


    public abstract Client findOneForUpdate(Long id);


    public abstract List<Client> findAll(Iterable<Long> ids);


    public abstract List<Client> findAll();


    public abstract long count();


    public abstract Page<Client> findAll(GlobalSearch globalSearch, Pageable pageable);


    public abstract Page<Client> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable);


    public abstract Client addToStorageTypes(Client client, Iterable<Long> storageTypesToAdd);


    public abstract Client removeFromStorageTypes(Client client, Iterable<Long> storageTypesToRemove);


    public abstract Client setStorageTypes(Client client, Iterable<Long> storageTypes);


    public abstract Client addToUsers(Client client, Iterable<Long> usersToAdd);


    public abstract Client removeFromUsers(Client client, Iterable<Long> usersToRemove);


    public abstract Client setUsers(Client client, Iterable<Long> users);
}
