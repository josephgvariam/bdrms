package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.InventoryItemService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.repository.InventoryItemRepository;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * = InventoryItemServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = InventoryItemService.class)
@Service
@Transactional(readOnly = true)
public class InventoryItemServiceImpl implements InventoryItemService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private InventoryItemRepository inventoryItemRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param inventoryItemRepository
     */
    @Autowired
    public InventoryItemServiceImpl(InventoryItemRepository inventoryItemRepository) {
        setInventoryItemRepository(inventoryItemRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return InventoryItemRepository
     */
    public InventoryItemRepository getInventoryItemRepository() {
        return inventoryItemRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItemRepository
     */
    public void setInventoryItemRepository(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryitem
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(InventoryItem inventoryitem) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItem
     */
    @Transactional
    public void delete(InventoryItem inventoryItem) {
        // Clear bidirectional many-to-many child relationship with Request
        for (Request item : inventoryItem.getRequests()) {
            item.getInventoryItems().remove(inventoryItem);
        }
        getInventoryItemRepository().delete(inventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<InventoryItem> save(Iterable<InventoryItem> entities) {
        return getInventoryItemRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<InventoryItem> toDelete = getInventoryItemRepository().findAll(ids);
        getInventoryItemRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return InventoryItem
     */
    @Transactional
    public InventoryItem save(InventoryItem entity) {
        return getInventoryItemRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return InventoryItem
     */
    public InventoryItem findOne(Long id) {
        return getInventoryItemRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return InventoryItem
     */
    public InventoryItem findOneForUpdate(Long id) {
        return getInventoryItemRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<InventoryItem> findAll(Iterable<Long> ids) {
        return getInventoryItemRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<InventoryItem> findAll() {
        return getInventoryItemRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getInventoryItemRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<InventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getInventoryItemRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<InventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getInventoryItemRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requests
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<InventoryItem> findByRequestsContains(Request requests, GlobalSearch globalSearch, Pageable pageable) {
        return getInventoryItemRepository().findByRequestsContains(requests, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requests
     * @return Long
     */
    public long countByRequestsContains(Request requests) {
        return getInventoryItemRepository().countByRequestsContains(requests);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<InventoryItem> getEntityType() {
        return InventoryItem.class;
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
