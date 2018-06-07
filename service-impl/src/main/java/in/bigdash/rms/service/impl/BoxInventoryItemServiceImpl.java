package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.BoxInventoryItemService;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import in.bigdash.rms.repository.BoxInventoryItemRepository;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class BoxInventoryItemServiceImpl implements BoxInventoryItemService {


    private BoxInventoryItemRepository boxInventoryItemRepository;


    @Autowired
    public BoxInventoryItemServiceImpl(BoxInventoryItemRepository boxInventoryItemRepository) {
        setBoxInventoryItemRepository(boxInventoryItemRepository);
    }


    public BoxInventoryItemRepository getBoxInventoryItemRepository() {
        return boxInventoryItemRepository;
    }


    public void setBoxInventoryItemRepository(BoxInventoryItemRepository boxInventoryItemRepository) {
        this.boxInventoryItemRepository = boxInventoryItemRepository;
    }


    public Map<String, List<MessageI18n>> validate(BoxInventoryItem boxinventoryitem) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(BoxInventoryItem boxInventoryItem) {
        getBoxInventoryItemRepository().delete(boxInventoryItem);
    }


    @Transactional
    public List<BoxInventoryItem> save(Iterable<BoxInventoryItem> entities) {
        return getBoxInventoryItemRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<BoxInventoryItem> toDelete = getBoxInventoryItemRepository().findAll(ids);
        getBoxInventoryItemRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public BoxInventoryItem save(BoxInventoryItem entity) {
        return getBoxInventoryItemRepository().save(entity);
    }


    public BoxInventoryItem findOne(Long id) {
        return getBoxInventoryItemRepository().findOne(id);
    }


    public BoxInventoryItem findOneForUpdate(Long id) {
        return getBoxInventoryItemRepository().findOneDetached(id);
    }


    public List<BoxInventoryItem> findAll(Iterable<Long> ids) {
        return getBoxInventoryItemRepository().findAll(ids);
    }


    public List<BoxInventoryItem> findAll() {
        return getBoxInventoryItemRepository().findAll();
    }


    public long count() {
        return getBoxInventoryItemRepository().count();
    }


    public Page<BoxInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getBoxInventoryItemRepository().findAll(globalSearch, pageable);
    }


    public Page<BoxInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getBoxInventoryItemRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<BoxInventoryItem> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable) {
        return getBoxInventoryItemRepository().findByBox(box, globalSearch, pageable);
    }


    public long countByBox(Box box) {
        return getBoxInventoryItemRepository().countByBox(box);
    }


    public Class<BoxInventoryItem> getEntityType() {
        return BoxInventoryItem.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
