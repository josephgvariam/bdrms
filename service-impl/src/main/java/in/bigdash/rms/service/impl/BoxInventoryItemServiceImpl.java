package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.BoxInventoryItemService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
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

/**
 * = BoxInventoryItemServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = BoxInventoryItemService.class)
@Service
@Transactional(readOnly = true)
public class BoxInventoryItemServiceImpl implements BoxInventoryItemService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private BoxInventoryItemRepository boxInventoryItemRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param boxInventoryItemRepository
     */
    @Autowired
    public BoxInventoryItemServiceImpl(BoxInventoryItemRepository boxInventoryItemRepository) {
        setBoxInventoryItemRepository(boxInventoryItemRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return BoxInventoryItemRepository
     */
    public BoxInventoryItemRepository getBoxInventoryItemRepository() {
        return boxInventoryItemRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxInventoryItemRepository
     */
    public void setBoxInventoryItemRepository(BoxInventoryItemRepository boxInventoryItemRepository) {
        this.boxInventoryItemRepository = boxInventoryItemRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxinventoryitem
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(BoxInventoryItem boxinventoryitem) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxInventoryItem
     */
    @Transactional
    public void delete(BoxInventoryItem boxInventoryItem) {
        getBoxInventoryItemRepository().delete(boxInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<BoxInventoryItem> save(Iterable<BoxInventoryItem> entities) {
        return getBoxInventoryItemRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<BoxInventoryItem> toDelete = getBoxInventoryItemRepository().findAll(ids);
        getBoxInventoryItemRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return BoxInventoryItem
     */
    @Transactional
    public BoxInventoryItem save(BoxInventoryItem entity) {
        return getBoxInventoryItemRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return BoxInventoryItem
     */
    public BoxInventoryItem findOne(Long id) {
        return getBoxInventoryItemRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return BoxInventoryItem
     */
    public BoxInventoryItem findOneForUpdate(Long id) {
        return getBoxInventoryItemRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<BoxInventoryItem> findAll(Iterable<Long> ids) {
        return getBoxInventoryItemRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<BoxInventoryItem> findAll() {
        return getBoxInventoryItemRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getBoxInventoryItemRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<BoxInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getBoxInventoryItemRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<BoxInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getBoxInventoryItemRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<BoxInventoryItem> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable) {
        return getBoxInventoryItemRepository().findByBox(box, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @return Long
     */
    public long countByBox(Box box) {
        return getBoxInventoryItemRepository().countByBox(box);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<BoxInventoryItem> getEntityType() {
        return BoxInventoryItem.class;
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
