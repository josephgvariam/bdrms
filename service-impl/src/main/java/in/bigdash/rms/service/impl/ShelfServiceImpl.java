package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.ShelfService;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.Facility;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.repository.shelf.ShelfRepository;
import in.bigdash.rms.service.api.BoxService;
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
public class ShelfServiceImpl implements ShelfService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private ShelfRepository shelfRepository;


    private BoxService boxService;


    @Autowired
    public ShelfServiceImpl(ShelfRepository shelfRepository, @Lazy BoxService boxService) {
        setShelfRepository(shelfRepository);
        setBoxService(boxService);
    }


    public ShelfRepository getShelfRepository() {
        return shelfRepository;
    }


    public void setShelfRepository(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }


    public BoxService getBoxService() {
        return boxService;
    }


    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }


    public Map<String, List<MessageI18n>> validate(Shelf shelf) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public Shelf addToBoxes(Shelf shelf, Iterable<Long> boxesToAdd) {
        List<Box> boxes = getBoxService().findAll(boxesToAdd);
        shelf.addToBoxes(boxes);
        return getShelfRepository().save(shelf);
    }


    @Transactional
    public Shelf removeFromBoxes(Shelf shelf, Iterable<Long> boxesToRemove) {
        List<Box> boxes = getBoxService().findAll(boxesToRemove);
        shelf.removeFromBoxes(boxes);
        return getShelfRepository().save(shelf);
    }


    @Transactional
    public Shelf setBoxes(Shelf shelf, Iterable<Long> boxes) {
        List<Box> items = getBoxService().findAll(boxes);
        Set<Box> currents = shelf.getBoxes();
        Set<Box> toRemove = new HashSet<Box>();
        for (Iterator<Box> iterator = currents.iterator(); iterator.hasNext(); ) {
            Box nextBox = iterator.next();
            if (items.contains(nextBox)) {
                items.remove(nextBox);
            } else {
                toRemove.add(nextBox);
            }
        }
        shelf.removeFromBoxes(toRemove);
        shelf.addToBoxes(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        shelf.setVersion(shelf.getVersion() + 1);
        return getShelfRepository().save(shelf);
    }


    @Transactional
    public void delete(Shelf shelf) {
        // Clear bidirectional many-to-one child relationship with Facility
        if (shelf.getFacility() != null) {
            shelf.getFacility().getShelves().remove(shelf);
        }
        // Clear bidirectional one-to-many parent relationship with Box
        for (Box item : shelf.getBoxes()) {
            item.setShelf(null);
        }
        getShelfRepository().delete(shelf);
    }


    @Transactional
    public List<Shelf> save(Iterable<Shelf> entities) {
        return getShelfRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Shelf> toDelete = getShelfRepository().findAll(ids);
        getShelfRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public Shelf save(Shelf entity) {
        return getShelfRepository().save(entity);
    }


    public Shelf findOne(Long id) {
        return getShelfRepository().findOne(id);
    }


    public Shelf findOneForUpdate(Long id) {
        return getShelfRepository().findOneDetached(id);
    }


    public List<Shelf> findAll(Iterable<Long> ids) {
        return getShelfRepository().findAll(ids);
    }


    public List<Shelf> findAll() {
        return getShelfRepository().findAll();
    }


    public long count() {
        return getShelfRepository().count();
    }


    public Page<Shelf> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getShelfRepository().findAll(globalSearch, pageable);
    }


    public Page<Shelf> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getShelfRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Page<Shelf> findByFacility(Facility facility, GlobalSearch globalSearch, Pageable pageable) {
        return getShelfRepository().findByFacility(facility, globalSearch, pageable);
    }


    public long countByFacility(Facility facility) {
        return getShelfRepository().countByFacility(facility);
    }


    public Class<Shelf> getEntityType() {
        return Shelf.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
