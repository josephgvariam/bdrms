package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.BoxService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.repository.BoxRepository;
import in.bigdash.rms.service.api.FileService;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RooServiceImpl(service = BoxService.class)
@Service
@Transactional(readOnly = true)
public class BoxServiceImpl implements BoxService {


    private FileService fileService;


    private BoxRepository boxRepository;


    @Autowired
    public BoxServiceImpl(BoxRepository boxRepository, @Lazy FileService fileService) {
        setBoxRepository(boxRepository);
        setFileService(fileService);
    }


    public BoxRepository getBoxRepository() {
        return boxRepository;
    }


    public void setBoxRepository(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }


    public FileService getFileService() {
        return fileService;
    }


    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }


    public Map<String, List<MessageI18n>> validate(Box box) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public Box addToFiles(Box box, Iterable<Long> filesToAdd) {
        List<File> files = getFileService().findAll(filesToAdd);
        box.addToFiles(files);
        return getBoxRepository().save(box);
    }


    @Transactional
    public Box removeFromFiles(Box box, Iterable<Long> filesToRemove) {
        List<File> files = getFileService().findAll(filesToRemove);
        box.removeFromFiles(files);
        return getBoxRepository().save(box);
    }


    @Transactional
    public Box setFiles(Box box, Iterable<Long> files) {
        List<File> items = getFileService().findAll(files);
        Set<File> currents = box.getFiles();
        Set<File> toRemove = new HashSet<File>();
        for (Iterator<File> iterator = currents.iterator(); iterator.hasNext(); ) {
            File nextFile = iterator.next();
            if (items.contains(nextFile)) {
                items.remove(nextFile);
            } else {
                toRemove.add(nextFile);
            }
        }
        box.removeFromFiles(toRemove);
        box.addToFiles(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        box.setVersion(box.getVersion() + 1);
        return getBoxRepository().save(box);
    }


    @Transactional
    public void delete(Box box) {
        // Clear bidirectional many-to-one child relationship with Shelf
        if (box.getShelf() != null) {
            box.getShelf().getBoxes().remove(box);
        }
        // Clear bidirectional one-to-many parent relationship with File
        for (File item : box.getFiles()) {
            item.setBox(null);
        }
        // Clear bidirectional one-to-one parent relationship with BoxInventoryItem
        box.removeFromInventoryItem();
        getBoxRepository().delete(box);
    }


    @Transactional
    public List<Box> save(Iterable<Box> entities) {
        return getBoxRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Box> toDelete = getBoxRepository().findAll(ids);
        getBoxRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public Box save(Box entity) {
        // Ensure the relationships are maintained
        entity.addToInventoryItem(entity.getInventoryItem());
        return getBoxRepository().save(entity);
    }


    public Box findOne(Long id) {
        return getBoxRepository().findOne(id);
    }


    public Box findOneForUpdate(Long id) {
        return getBoxRepository().findOneDetached(id);
    }


    public List<Box> findAll(Iterable<Long> ids) {
        return getBoxRepository().findAll(ids);
    }


    public List<Box> findAll() {
        return getBoxRepository().findAll();
    }


    public long count() {
        return getBoxRepository().count();
    }


    public Page<Box> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getBoxRepository().findAll(globalSearch, pageable);
    }


    public Page<Box> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getBoxRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<Box> findByShelf(Shelf shelf, GlobalSearch globalSearch, Pageable pageable) {
        return getBoxRepository().findByShelf(shelf, globalSearch, pageable);
    }


    public long countByShelf(Shelf shelf) {
        return getBoxRepository().countByShelf(shelf);
    }


    public Class<Box> getEntityType() {
        return Box.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
