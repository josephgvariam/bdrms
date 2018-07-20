package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.InventoryItemService;
import in.bigdash.rms.model.inventory.InventoryItem;
import in.bigdash.rms.model.request.Request;
import in.bigdash.rms.repository.inventory.InventoryItemRepository;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class InventoryItemServiceImpl implements InventoryItemService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private InventoryItemRepository inventoryItemRepository;


    @Autowired
    public InventoryItemServiceImpl(InventoryItemRepository inventoryItemRepository) {
        setInventoryItemRepository(inventoryItemRepository);
    }


    public InventoryItemRepository getInventoryItemRepository() {
        return inventoryItemRepository;
    }


    public void setInventoryItemRepository(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }


    public Map<String, List<MessageI18n>> validate(InventoryItem inventoryitem) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(InventoryItem inventoryItem) {
        // Clear bidirectional many-to-many child relationship with Request
        for (Request item : inventoryItem.getRequests()) {
            item.getInventoryItems().remove(inventoryItem);
        }
        getInventoryItemRepository().delete(inventoryItem);
    }


    @Transactional
    public List<InventoryItem> save(Iterable<InventoryItem> entities) {
        return getInventoryItemRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<InventoryItem> toDelete = getInventoryItemRepository().findAll(ids);
        getInventoryItemRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public InventoryItem save(InventoryItem entity) {
        return getInventoryItemRepository().save(entity);
    }


    public InventoryItem findOne(Long id) {
        return getInventoryItemRepository().findOne(id);
    }


    public InventoryItem findOneForUpdate(Long id) {
        return getInventoryItemRepository().findOneDetached(id);
    }


    public List<InventoryItem> findAll(Iterable<Long> ids) {
        return getInventoryItemRepository().findAll(ids);
    }


    public List<InventoryItem> findAll() {
        return getInventoryItemRepository().findAll();
    }


    public long count() {
        return getInventoryItemRepository().count();
    }


    public Page<InventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getInventoryItemRepository().findAll(globalSearch, pageable);
    }

    public List<InventoryItem> findByRequestTypeAndStorageType(String requestType, String storageType) {
        return getInventoryItemRepository().findByRequestTypeAndStorageType(requestType, storageType);
    }


    public Page<InventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getInventoryItemRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<InventoryItem> findByRequestsContains(Request requests, GlobalSearch globalSearch, Pageable pageable) {
        return getInventoryItemRepository().findByRequestsContains(requests, globalSearch, pageable);
    }


    public long countByRequestsContains(Request requests) {
        return getInventoryItemRepository().countByRequestsContains(requests);
    }


    public Class<InventoryItem> getEntityType() {
        return InventoryItem.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
