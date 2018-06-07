package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.DocumentService;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.model.File;
import in.bigdash.rms.repository.document.DocumentRepository;
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
public class DocumentServiceImpl implements DocumentService {


    private DocumentRepository documentRepository;


    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        setDocumentRepository(documentRepository);
    }


    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }


    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public Map<String, List<MessageI18n>> validate(Document document) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


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


    @Transactional
    public List<Document> save(Iterable<Document> entities) {
        return getDocumentRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Document> toDelete = getDocumentRepository().findAll(ids);
        getDocumentRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public Document save(Document entity) {
        // Ensure the relationships are maintained
        entity.addToInventoryItem(entity.getInventoryItem());
        return getDocumentRepository().save(entity);
    }


    public Document findOne(Long id) {
        return getDocumentRepository().findOne(id);
    }


    public Document findOneForUpdate(Long id) {
        return getDocumentRepository().findOneDetached(id);
    }


    public List<Document> findAll(Iterable<Long> ids) {
        return getDocumentRepository().findAll(ids);
    }


    public List<Document> findAll() {
        return getDocumentRepository().findAll();
    }


    public long count() {
        return getDocumentRepository().count();
    }


    public Page<Document> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentRepository().findAll(globalSearch, pageable);
    }


    public Page<Document> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<Document> findByFile(File file, GlobalSearch globalSearch, Pageable pageable) {
        return getDocumentRepository().findByFile(file, globalSearch, pageable);
    }


    public long countByFile(File file) {
        return getDocumentRepository().countByFile(file);
    }


    public Class<Document> getEntityType() {
        return Document.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
