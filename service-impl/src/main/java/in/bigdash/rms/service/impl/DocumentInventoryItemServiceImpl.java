package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.DocumentInventoryItemService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import in.bigdash.rms.repository.DocumentInventoryItemRepository;
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
 * = DocumentInventoryItemServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = DocumentInventoryItemService.class)
@Service
@Transactional(readOnly = true)
public class DocumentInventoryItemServiceImpl implements DocumentInventoryItemService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DocumentInventoryItemRepository documentInventoryItemRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param documentInventoryItemRepository
     */
    @Autowired
    public DocumentInventoryItemServiceImpl(DocumentInventoryItemRepository documentInventoryItemRepository) {
        setDocumentInventoryItemRepository(documentInventoryItemRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DocumentInventoryItemRepository
     */
    public DocumentInventoryItemRepository getDocumentInventoryItemRepository() {
        return documentInventoryItemRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItemRepository
     */
    public void setDocumentInventoryItemRepository(DocumentInventoryItemRepository documentInventoryItemRepository) {
        this.documentInventoryItemRepository = documentInventoryItemRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentinventoryitem
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(DocumentInventoryItem documentinventoryitem) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentInventoryItem
     */
    @Transactional
    public void delete(DocumentInventoryItem documentInventoryItem) {
        getDocumentInventoryItemRepository().delete(documentInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<DocumentInventoryItem> save(Iterable<DocumentInventoryItem> entities) {
        return getDocumentInventoryItemRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<DocumentInventoryItem> toDelete = getDocumentInventoryItemRepository().findAll(ids);
        getDocumentInventoryItemRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return DocumentInventoryItem
     */
    @Transactional
    public DocumentInventoryItem save(DocumentInventoryItem entity) {
        return getDocumentInventoryItemRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return DocumentInventoryItem
     */
    public DocumentInventoryItem findOne(Long id) {
        return getDocumentInventoryItemRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return DocumentInventoryItem
     */
    public DocumentInventoryItem findOneForUpdate(Long id) {
        return getDocumentInventoryItemRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<DocumentInventoryItem> findAll(Iterable<Long> ids) {
        return getDocumentInventoryItemRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<DocumentInventoryItem> findAll() {
        return getDocumentInventoryItemRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getDocumentInventoryItemRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<DocumentInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentInventoryItemRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<DocumentInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentInventoryItemRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param document
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<DocumentInventoryItem> findByDocument(Document document, GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentInventoryItemRepository().findByDocument(document, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param document
     * @return Long
     */
    public long countByDocument(Document document) {
        return getDocumentInventoryItemRepository().countByDocument(document);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<DocumentInventoryItem> getEntityType() {
        return DocumentInventoryItem.class;
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
