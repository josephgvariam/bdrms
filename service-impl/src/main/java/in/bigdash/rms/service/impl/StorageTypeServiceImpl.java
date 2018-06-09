package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.StorageTypeService;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.repository.storagetype.StorageTypeRepository;
import in.bigdash.rms.service.api.RequestService;
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
public class StorageTypeServiceImpl implements StorageTypeService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private RequestService requestService;


    private StorageTypeRepository storageTypeRepository;


    @Autowired
    public StorageTypeServiceImpl(StorageTypeRepository storageTypeRepository, @Lazy RequestService requestService) {
        setStorageTypeRepository(storageTypeRepository);
        setRequestService(requestService);
    }


    public StorageTypeRepository getStorageTypeRepository() {
        return storageTypeRepository;
    }


    public void setStorageTypeRepository(StorageTypeRepository storageTypeRepository) {
        this.storageTypeRepository = storageTypeRepository;
    }


    public RequestService getRequestService() {
        return requestService;
    }


    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }


    public Map<String, List<MessageI18n>> validate(StorageType storagetype) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public StorageType addToRequests(StorageType storageType, Iterable<Long> requestsToAdd) {
        List<Request> requests = getRequestService().findAll(requestsToAdd);
        storageType.addToRequests(requests);
        return getStorageTypeRepository().save(storageType);
    }


    @Transactional
    public StorageType removeFromRequests(StorageType storageType, Iterable<Long> requestsToRemove) {
        List<Request> requests = getRequestService().findAll(requestsToRemove);
        storageType.removeFromRequests(requests);
        return getStorageTypeRepository().save(storageType);
    }


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


    @Transactional
    public List<StorageType> save(Iterable<StorageType> entities) {
        return getStorageTypeRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<StorageType> toDelete = getStorageTypeRepository().findAll(ids);
        getStorageTypeRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public StorageType save(StorageType entity) {
        return getStorageTypeRepository().save(entity);
    }


    public StorageType findOne(Long id) {
        return getStorageTypeRepository().findOne(id);
    }


    public StorageType findOneForUpdate(Long id) {
        return getStorageTypeRepository().findOneDetached(id);
    }


    public List<StorageType> findAll(Iterable<Long> ids) {
        return getStorageTypeRepository().findAll(ids);
    }


    public List<StorageType> findAll() {
        return getStorageTypeRepository().findAll();
    }


    public long count() {
        return getStorageTypeRepository().count();
    }


    public Page<StorageType> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getStorageTypeRepository().findAll(globalSearch, pageable);
    }

    public Page<StorageType> findAllByCurrentUser(GlobalSearch globalSearch, Pageable pageable) {
        return getStorageTypeRepository().findAllByCurrentUser(globalSearch, pageable);
    }

    public Page<StorageType> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getStorageTypeRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<StorageType> findByClientsContains(Client clients, GlobalSearch globalSearch, Pageable pageable) {
        return getStorageTypeRepository().findByClientsContains(clients, globalSearch, pageable);
    }


    public long countByClientsContains(Client clients) {
        return getStorageTypeRepository().countByClientsContains(clients);
    }


    public Class<StorageType> getEntityType() {
        return StorageType.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
