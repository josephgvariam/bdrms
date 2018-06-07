package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.RequestService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.repository.RequestRepository;
import in.bigdash.rms.service.api.InventoryItemService;
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
 * = RequestServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = RequestService.class)
@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RequestRepository requestRepository;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private InventoryItemService inventoryItemService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param requestRepository
     * @param inventoryItemService
     */
    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, @Lazy InventoryItemService inventoryItemService) {
        setRequestRepository(requestRepository);
        setInventoryItemService(inventoryItemService);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RequestRepository
     */
    public RequestRepository getRequestRepository() {
        return requestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestRepository
     */
    public void setRequestRepository(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return InventoryItemService
     */
    public InventoryItemService getInventoryItemService() {
        return inventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItemService
     */
    public void setInventoryItemService(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(Request request) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @param inventoryItemsToAdd
     * @return Request
     */
    @Transactional
    public Request addToInventoryItems(Request request, Iterable<Long> inventoryItemsToAdd) {
        List<InventoryItem> inventoryItems = getInventoryItemService().findAll(inventoryItemsToAdd);
        request.addToInventoryItems(inventoryItems);
        return getRequestRepository().save(request);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @param inventoryItemsToRemove
     * @return Request
     */
    @Transactional
    public Request removeFromInventoryItems(Request request, Iterable<Long> inventoryItemsToRemove) {
        List<InventoryItem> inventoryItems = getInventoryItemService().findAll(inventoryItemsToRemove);
        request.removeFromInventoryItems(inventoryItems);
        return getRequestRepository().save(request);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     * @param inventoryItems
     * @return Request
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param request
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<Request> save(Iterable<Request> entities) {
        return getRequestRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Request> toDelete = getRequestRepository().findAll(ids);
        getRequestRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return Request
     */
    @Transactional
    public Request save(Request entity) {
        return getRequestRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Request
     */
    public Request findOne(Long id) {
        return getRequestRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Request
     */
    public Request findOneForUpdate(Long id) {
        return getRequestRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<Request> findAll(Iterable<Long> ids) {
        return getRequestRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<Request> findAll() {
        return getRequestRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getRequestRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Request> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Request> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Request> findByStorageType(StorageType storageType, GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findByStorageType(storageType, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param userAssigned
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Request> findByUserAssigned(User userAssigned, GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findByUserAssigned(userAssigned, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param userCreated
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Request> findByUserCreated(User userCreated, GlobalSearch globalSearch, Pageable pageable) {
        return getRequestRepository().findByUserCreated(userCreated, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @return Long
     */
    public long countByStorageType(StorageType storageType) {
        return getRequestRepository().countByStorageType(storageType);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param userAssigned
     * @return Long
     */
    public long countByUserAssigned(User userAssigned) {
        return getRequestRepository().countByUserAssigned(userAssigned);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param userCreated
     * @return Long
     */
    public long countByUserCreated(User userCreated) {
        return getRequestRepository().countByUserCreated(userCreated);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<Request> getEntityType() {
        return Request.class;
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
