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


@RooServiceImpl(service = FileInventoryItemService.class)
@Service
@Transactional(readOnly = true)
public class FileInventoryItemServiceImpl implements FileInventoryItemService {


    private FileInventoryItemRepository fileInventoryItemRepository;


    @Autowired
    public FileInventoryItemServiceImpl(FileInventoryItemRepository fileInventoryItemRepository) {
        setFileInventoryItemRepository(fileInventoryItemRepository);
    }


    public FileInventoryItemRepository getFileInventoryItemRepository() {
        return fileInventoryItemRepository;
    }


    public void setFileInventoryItemRepository(FileInventoryItemRepository fileInventoryItemRepository) {
        this.fileInventoryItemRepository = fileInventoryItemRepository;
    }


    public Map<String, List<MessageI18n>> validate(FileInventoryItem fileinventoryitem) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(FileInventoryItem fileInventoryItem) {
        getFileInventoryItemRepository().delete(fileInventoryItem);
    }


    @Transactional
    public List<FileInventoryItem> save(Iterable<FileInventoryItem> entities) {
        return getFileInventoryItemRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<FileInventoryItem> toDelete = getFileInventoryItemRepository().findAll(ids);
        getFileInventoryItemRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public FileInventoryItem save(FileInventoryItem entity) {
        return getFileInventoryItemRepository().save(entity);
    }


    public FileInventoryItem findOne(Long id) {
        return getFileInventoryItemRepository().findOne(id);
    }


    public FileInventoryItem findOneForUpdate(Long id) {
        return getFileInventoryItemRepository().findOneDetached(id);
    }


    public List<FileInventoryItem> findAll(Iterable<Long> ids) {
        return getFileInventoryItemRepository().findAll(ids);
    }


    public List<FileInventoryItem> findAll() {
        return getFileInventoryItemRepository().findAll();
    }


    public long count() {
        return getFileInventoryItemRepository().count();
    }


    public Page<FileInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getFileInventoryItemRepository().findAll(globalSearch, pageable);
    }


    public Page<FileInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getFileInventoryItemRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<FileInventoryItem> findByFile(File file, GlobalSearch globalSearch, Pageable pageable) {
        return getFileInventoryItemRepository().findByFile(file, globalSearch, pageable);
    }


    public long countByFile(File file) {
        return getFileInventoryItemRepository().countByFile(file);
    }


    public Class<FileInventoryItem> getEntityType() {
        return FileInventoryItem.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
