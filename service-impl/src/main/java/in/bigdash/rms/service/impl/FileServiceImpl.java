package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.FileService;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.model.File;
import in.bigdash.rms.repository.file.FileRepository;
import in.bigdash.rms.service.api.DocumentService;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private FileRepository fileRepository;


    private DocumentService documentService;


    @Autowired
    public FileServiceImpl(FileRepository fileRepository, @Lazy DocumentService documentService) {
        setFileRepository(fileRepository);
        setDocumentService(documentService);
    }


    public FileRepository getFileRepository() {
        return fileRepository;
    }


    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public DocumentService getDocumentService() {
        return documentService;
    }


    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }


    public Map<String, List<MessageI18n>> validate(File file) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public File addToDocuments(File file, Iterable<Long> documentsToAdd) {
        List<Document> documents = getDocumentService().findAll(documentsToAdd);
        file.addToDocuments(documents);
        return getFileRepository().save(file);
    }


    @Transactional
    public File removeFromDocuments(File file, Iterable<Long> documentsToRemove) {
        List<Document> documents = getDocumentService().findAll(documentsToRemove);
        file.removeFromDocuments(documents);
        return getFileRepository().save(file);
    }


    @Transactional
    public File setDocuments(File file, Iterable<Long> documents) {
        List<Document> items = getDocumentService().findAll(documents);
        Set<Document> currents = file.getDocuments();
        Set<Document> toRemove = new HashSet<Document>();
        for (Iterator<Document> iterator = currents.iterator(); iterator.hasNext(); ) {
            Document nextDocument = iterator.next();
            if (items.contains(nextDocument)) {
                items.remove(nextDocument);
            } else {
                toRemove.add(nextDocument);
            }
        }
        file.removeFromDocuments(toRemove);
        file.addToDocuments(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        file.setVersion(file.getVersion() + 1);
        return getFileRepository().save(file);
    }


    @Transactional
    public void delete(File file) {
        // Clear bidirectional many-to-one child relationship with Box
        if (file.getBox() != null) {
            file.getBox().getFiles().remove(file);
        }
        // Clear bidirectional one-to-many parent relationship with Document
        for (Document item : file.getDocuments()) {
            item.setFile(null);
        }
        // Clear bidirectional one-to-one parent relationship with FileInventoryItem
        file.removeFromInventoryItem();
        getFileRepository().delete(file);
    }


    @Transactional
    public List<File> save(Iterable<File> entities) {
        return getFileRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<File> toDelete = getFileRepository().findAll(ids);
        getFileRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public File save(File entity) {
        // Ensure the relationships are maintained
        entity.addToInventoryItem(entity.getInventoryItem());
        return getFileRepository().save(entity);
    }


    public File findOne(Long id) {
        return getFileRepository().findOne(id);
    }


    public File findOneForUpdate(Long id) {
        return getFileRepository().findOneDetached(id);
    }


    public List<File> findAll(Iterable<Long> ids) {
        return getFileRepository().findAll(ids);
    }


    public List<File> findAll() {
        return getFileRepository().findAll();
    }


    public long count() {
        return getFileRepository().count();
    }


    public Page<File> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getFileRepository().findAll(globalSearch, pageable);
    }


    public Page<File> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getFileRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<File> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable) {
        return getFileRepository().findByBox(box, globalSearch, pageable);
    }


    public long countByBox(Box box) {
        return getFileRepository().countByBox(box);
    }


    public Class<File> getEntityType() {
        return File.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
