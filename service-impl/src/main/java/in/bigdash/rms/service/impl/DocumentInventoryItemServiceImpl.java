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


@RooServiceImpl(service = DocumentInventoryItemService.class)
@Service
@Transactional(readOnly = true)
public class DocumentInventoryItemServiceImpl implements DocumentInventoryItemService {


    private DocumentInventoryItemRepository documentInventoryItemRepository;


    @Autowired
    public DocumentInventoryItemServiceImpl(DocumentInventoryItemRepository documentInventoryItemRepository) {
        setDocumentInventoryItemRepository(documentInventoryItemRepository);
    }


    public DocumentInventoryItemRepository getDocumentInventoryItemRepository() {
        return documentInventoryItemRepository;
    }


    public void setDocumentInventoryItemRepository(DocumentInventoryItemRepository documentInventoryItemRepository) {
        this.documentInventoryItemRepository = documentInventoryItemRepository;
    }


    public Map<String, List<MessageI18n>> validate(DocumentInventoryItem documentinventoryitem) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(DocumentInventoryItem documentInventoryItem) {
        getDocumentInventoryItemRepository().delete(documentInventoryItem);
    }


    @Transactional
    public List<DocumentInventoryItem> save(Iterable<DocumentInventoryItem> entities) {
        return getDocumentInventoryItemRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<DocumentInventoryItem> toDelete = getDocumentInventoryItemRepository().findAll(ids);
        getDocumentInventoryItemRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public DocumentInventoryItem save(DocumentInventoryItem entity) {
        return getDocumentInventoryItemRepository().save(entity);
    }


    public DocumentInventoryItem findOne(Long id) {
        return getDocumentInventoryItemRepository().findOne(id);
    }


    public DocumentInventoryItem findOneForUpdate(Long id) {
        return getDocumentInventoryItemRepository().findOneDetached(id);
    }


    public List<DocumentInventoryItem> findAll(Iterable<Long> ids) {
        return getDocumentInventoryItemRepository().findAll(ids);
    }


    public List<DocumentInventoryItem> findAll() {
        return getDocumentInventoryItemRepository().findAll();
    }


    public long count() {
        return getDocumentInventoryItemRepository().count();
    }


    public Page<DocumentInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentInventoryItemRepository().findAll(globalSearch, pageable);
    }


    public Page<DocumentInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentInventoryItemRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<DocumentInventoryItem> findByDocument(Document document, GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentInventoryItemRepository().findByDocument(document, globalSearch, pageable);
    }


    public long countByDocument(Document document) {
        return getDocumentInventoryItemRepository().countByDocument(document);
    }


    public Class<DocumentInventoryItem> getEntityType() {
        return DocumentInventoryItem.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
