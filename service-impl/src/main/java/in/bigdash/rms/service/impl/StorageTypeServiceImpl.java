package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.StorageTypeService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.repository.StorageTypeRepository;
import in.bigdash.rms.service.api.RequestService;
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
 * = StorageTypeServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = StorageTypeService.class)
@Service
@Transactional(readOnly = true)
public class StorageTypeServiceImpl implements StorageTypeService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RequestService requestService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private StorageTypeRepository storageTypeRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param storageTypeRepository
     * @param requestService
     */
    @Autowired
    public StorageTypeServiceImpl(StorageTypeRepository storageTypeRepository, @Lazy RequestService requestService) {
        setStorageTypeRepository(storageTypeRepository);
        setRequestService(requestService);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return StorageTypeRepository
     */
    public StorageTypeRepository getStorageTypeRepository() {
        return storageTypeRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageTypeRepository
     */
    public void setStorageTypeRepository(StorageTypeRepository storageTypeRepository) {
        this.storageTypeRepository = storageTypeRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RequestService
     */
    public RequestService getRequestService() {
        return requestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestService
     */
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storagetype
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(StorageType storagetype) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param requestsToAdd
     * @return StorageType
     */
    @Transactional
    public StorageType addToRequests(StorageType storageType, Iterable<Long> requestsToAdd) {
        List<Request> requests = getRequestService().findAll(requestsToAdd);
        storageType.addToRequests(requests);
        return getStorageTypeRepository().save(storageType);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param requestsToRemove
     * @return StorageType
     */
    @Transactional
    public StorageType removeFromRequests(StorageType storageType, Iterable<Long> requestsToRemove) {
        List<Request> requests = getRequestService().findAll(requestsToRemove);
        storageType.removeFromRequests(requests);
        return getStorageTypeRepository().save(storageType);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param requests
     * @return StorageType
     */
    @Transactional
    public StorageType setRequests(StorageType storageType, Iterable<Long> requests) {
        List<Request> items = getRequestService().findAll(requests);
        Set<Request> currents = storageType.getRequests();
        Set<Request> toRemove = new HashSet<Request>();
        for (Iterator<Request> iterator = currents.iterator(); iterator.hasNext(); ) {
            Request nextRequest = iterator.next();
            if (items.contains(nextRequest)) {
                items.remove(nextRequest);
            } else {
                toRemove.add(nextRequest);
            }
        }
        storageType.removeFromRequests(toRemove);
        storageType.addToRequests(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        storageType.setVersion(storageType.getVersion() + 1);
        return getStorageTypeRepository().save(storageType);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     */
    @Transactional
    public void delete(StorageType storageType) {
        // Clear bidirectional many-to-many child relationship with Client
        for (Client item : storageType.getClients()) {
            item.getStorageTypes().remove(storageType);
        }
        // Clear bidirectional one-to-many parent relationship with Request
        for (Request item : storageType.getRequests()) {
            item.setStorageType(null);
        }
        getStorageTypeRepository().delete(storageType);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<StorageType> save(Iterable<StorageType> entities) {
        return getStorageTypeRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<StorageType> toDelete = getStorageTypeRepository().findAll(ids);
        getStorageTypeRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return StorageType
     */
    @Transactional
    public StorageType save(StorageType entity) {
        return getStorageTypeRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return StorageType
     */
    public StorageType findOne(Long id) {
        return getStorageTypeRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return StorageType
     */
    public StorageType findOneForUpdate(Long id) {
        return getStorageTypeRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<StorageType> findAll(Iterable<Long> ids) {
        return getStorageTypeRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<StorageType> findAll() {
        return getStorageTypeRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getStorageTypeRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<StorageType> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getStorageTypeRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<StorageType> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getStorageTypeRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param clients
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<StorageType> findByClientsContains(Client clients, GlobalSearch globalSearch, Pageable pageable) {
        return getStorageTypeRepository().findByClientsContains(clients, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param clients
     * @return Long
     */
    public long countByClientsContains(Client clients) {
        return getStorageTypeRepository().countByClientsContains(clients);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<StorageType> getEntityType() {
        return StorageType.class;
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
