package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.FileInventoryItemService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import in.bigdash.rms.repository.FileInventoryItemRepository;
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
 * = FileInventoryItemServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = FileInventoryItemService.class)
@Service
@Transactional(readOnly = true)
public class FileInventoryItemServiceImpl implements FileInventoryItemService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private FileInventoryItemRepository fileInventoryItemRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param fileInventoryItemRepository
     */
    @Autowired
    public FileInventoryItemServiceImpl(FileInventoryItemRepository fileInventoryItemRepository) {
        setFileInventoryItemRepository(fileInventoryItemRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return FileInventoryItemRepository
     */
    public FileInventoryItemRepository getFileInventoryItemRepository() {
        return fileInventoryItemRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItemRepository
     */
    public void setFileInventoryItemRepository(FileInventoryItemRepository fileInventoryItemRepository) {
        this.fileInventoryItemRepository = fileInventoryItemRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileinventoryitem
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(FileInventoryItem fileinventoryitem) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param fileInventoryItem
     */
    @Transactional
    public void delete(FileInventoryItem fileInventoryItem) {
        getFileInventoryItemRepository().delete(fileInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<FileInventoryItem> save(Iterable<FileInventoryItem> entities) {
        return getFileInventoryItemRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<FileInventoryItem> toDelete = getFileInventoryItemRepository().findAll(ids);
        getFileInventoryItemRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return FileInventoryItem
     */
    @Transactional
    public FileInventoryItem save(FileInventoryItem entity) {
        return getFileInventoryItemRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return FileInventoryItem
     */
    public FileInventoryItem findOne(Long id) {
        return getFileInventoryItemRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return FileInventoryItem
     */
    public FileInventoryItem findOneForUpdate(Long id) {
        return getFileInventoryItemRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<FileInventoryItem> findAll(Iterable<Long> ids) {
        return getFileInventoryItemRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<FileInventoryItem> findAll() {
        return getFileInventoryItemRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getFileInventoryItemRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<FileInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getFileInventoryItemRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<FileInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getFileInventoryItemRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<FileInventoryItem> findByFile(File file, GlobalSearch globalSearch, Pageable pageable) {
        return getFileInventoryItemRepository().findByFile(file, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @return Long
     */
    public long countByFile(File file) {
        return getFileInventoryItemRepository().countByFile(file);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<FileInventoryItem> getEntityType() {
        return FileInventoryItem.class;
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
