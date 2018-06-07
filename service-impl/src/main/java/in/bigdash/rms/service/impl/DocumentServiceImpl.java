package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.DocumentService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.model.File;
import in.bigdash.rms.repository.DocumentRepository;
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
 * = DocumentServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = DocumentService.class)
@Service
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DocumentRepository documentRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param documentRepository
     */
    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        setDocumentRepository(documentRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DocumentRepository
     */
    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentRepository
     */
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param document
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(Document document) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param document
     */
    @Transactional
    public void delete(Document document) {
        // Clear bidirectional many-to-one child relationship with File
        if (document.getFile() != null) {
            document.getFile().getDocuments().remove(document);
        }
        // Clear bidirectional one-to-one parent relationship with DocumentInventoryItem
        document.removeFromInventoryItem();
        getDocumentRepository().delete(document);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<Document> save(Iterable<Document> entities) {
        return getDocumentRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Document> toDelete = getDocumentRepository().findAll(ids);
        getDocumentRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return Document
     */
    @Transactional
    public Document save(Document entity) {
        // Ensure the relationships are maintained
        entity.addToInventoryItem(entity.getInventoryItem());
        return getDocumentRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Document
     */
    public Document findOne(Long id) {
        return getDocumentRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Document
     */
    public Document findOneForUpdate(Long id) {
        return getDocumentRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<Document> findAll(Iterable<Long> ids) {
        return getDocumentRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<Document> findAll() {
        return getDocumentRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getDocumentRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Document> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Document> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Document> findByFile(File file, GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentRepository().findByFile(file, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @return Long
     */
    public long countByFile(File file) {
        return getDocumentRepository().countByFile(file);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<Document> getEntityType() {
        return Document.class;
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
