package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.RequestService;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.repository.request.RequestRepository;
import in.bigdash.rms.service.api.InventoryItemService;
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
public class RequestServiceImpl implements RequestService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private RequestRepository requestRepository;


    private InventoryItemService inventoryItemService;


    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, @Lazy InventoryItemService inventoryItemService) {
        setRequestRepository(requestRepository);
        setInventoryItemService(inventoryItemService);
    }


    public RequestRepository getRequestRepository() {
        return requestRepository;
    }


    public void setRequestRepository(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    public InventoryItemService getInventoryItemService() {
        return inventoryItemService;
    }


    public void setInventoryItemService(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }


    public Map<String, List<MessageI18n>> validate(Request request) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public Request addToInventoryItems(Request request, Iterable<Long> inventoryItemsToAdd) {
        List<InventoryItem> inventoryItems = getInventoryItemService().findAll(inventoryItemsToAdd);
        request.addToInventoryItems(inventoryItems);
        return getRequestRepository().save(request);
    }


    @Transactional
    public Request removeFromInventoryItems(Request request, Iterable<Long> inventoryItemsToRemove) {
        List<InventoryItem> inventoryItems = getInventoryItemService().findAll(inventoryItemsToRemove);
        request.removeFromInventoryItems(inventoryItems);
        return getRequestRepository().save(request);
    }


    @Transactional
    public Request setInventoryItems(Request request, Iterable<Long> inventoryItems) {
        List<InventoryItem> items = getInventoryItemService().findAll(inventoryItems);
        Set<InventoryItem> currents = request.getInventoryItems();
        Set<InventoryItem> toRemove = new HashSet<InventoryItem>();
        for (Iterator<InventoryItem> iterator = currents.iterator(); iterator.hasNext(); ) {
            InventoryItem nextInventoryItem = iterator.next();
            if (items.contains(nextInventoryItem)) {
                items.remove(nextInventoryItem);
            } else {
                toRemove.add(nextInventoryItem);
            }
        }
        request.removeFromInventoryItems(toRemove);
        request.addToInventoryItems(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        request.setVersion(request.getVersion() + 1);
        return getRequestRepository().save(request);
    }


    @Transactional
    public void delete(Request request) {
        // Clear bidirectional many-to-one child relationship with User
        if (request.getUserAssigned() != null) {
            request.getUserAssigned().getRequestsAssigned().remove(request);
        }
        // Clear bidirectional many-to-one child relationship with StorageType
        if (request.getStorageType() != null) {
            request.getStorageType().getRequests().remove(request);
        }
        // Clear bidirectional many-to-one child relationship with User
        if (request.getUserCreated() != null) {
            request.getUserCreated().getRequestsCreated().remove(request);
        }
        // Clear bidirectional many-to-many parent relationship with InventoryItem
        for (InventoryItem item : request.getInventoryItems()) {
            item.getRequests().remove(request);
        }
        getRequestRepository().delete(request);
    }


    @Transactional
    public List<Request> save(Iterable<Request> entities) {
        return getRequestRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Request> toDelete = getRequestRepository().findAll(ids);
        getRequestRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public Request save(Request entity) {
        return getRequestRepository().save(entity);
    }


    public Request findOne(Long id) {
        return getRequestRepository().findOne(id);
    }


    public Request findOneForUpdate(Long id) {
        return getRequestRepository().findOneDetached(id);
    }


    public List<Request> findAll(Iterable<Long> ids) {
        return getRequestRepository().findAll(ids);
    }


    public List<Request> findAll() {
        return getRequestRepository().findAll();
    }


    public long count() {
        return getRequestRepository().count();
    }


    public Page<Request> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findAll(globalSearch, pageable);
    }


    public Page<Request> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<Request> findByStorageType(StorageType storageType, GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findByStorageType(storageType, globalSearch, pageable);
    }


    public Page<Request> findByUserAssigned(User userAssigned, GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findByUserAssigned(userAssigned, globalSearch, pageable);
    }


    public Page<Request> findByUserCreated(User userCreated, GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findByUserCreated(userCreated, globalSearch, pageable);
    }


    public long countByStorageType(StorageType storageType) {
        return getRequestRepository().countByStorageType(storageType);
    }


    public long countByUserAssigned(User userAssigned) {
        return getRequestRepository().countByUserAssigned(userAssigned);
    }


    public long countByUserCreated(User userCreated) {
        return getRequestRepository().countByUserCreated(userCreated);
    }


    public Class<Request> getEntityType() {
        return Request.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
