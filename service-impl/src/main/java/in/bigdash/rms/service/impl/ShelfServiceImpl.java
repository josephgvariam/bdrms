package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.ShelfService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.Facility;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.repository.ShelfRepository;
import in.bigdash.rms.service.api.BoxService;
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

/**
 * = ShelfServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = ShelfService.class)
@Service
@Transactional(readOnly = true)
public class ShelfServiceImpl implements ShelfService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ShelfRepository shelfRepository;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private BoxService boxService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param shelfRepository
     * @param boxService
     */
    @Autowired
    public ShelfServiceImpl(ShelfRepository shelfRepository, @Lazy BoxService boxService) {
        setShelfRepository(shelfRepository);
        setBoxService(boxService);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ShelfRepository
     */
    public ShelfRepository getShelfRepository() {
        return shelfRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelfRepository
     */
    public void setShelfRepository(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return BoxService
     */
    public BoxService getBoxService() {
        return boxService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxService
     */
    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(Shelf shelf) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param boxesToAdd
     * @return Shelf
     */
    @Transactional
    public Shelf addToBoxes(Shelf shelf, Iterable<Long> boxesToAdd) {
        List<Box> boxes = getBoxService().findAll(boxesToAdd);
        shelf.addToBoxes(boxes);
        return getShelfRepository().save(shelf);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param boxesToRemove
     * @return Shelf
     */
    @Transactional
    public Shelf removeFromBoxes(Shelf shelf, Iterable<Long> boxesToRemove) {
        List<Box> boxes = getBoxService().findAll(boxesToRemove);
        shelf.removeFromBoxes(boxes);
        return getShelfRepository().save(shelf);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param boxes
     * @return Shelf
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<Shelf> save(Iterable<Shelf> entities) {
        return getShelfRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Shelf> toDelete = getShelfRepository().findAll(ids);
        getShelfRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return Shelf
     */
    @Transactional
    public Shelf save(Shelf entity) {
        return getShelfRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Shelf
     */
    public Shelf findOne(Long id) {
        return getShelfRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Shelf
     */
    public Shelf findOneForUpdate(Long id) {
        return getShelfRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<Shelf> findAll(Iterable<Long> ids) {
        return getShelfRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<Shelf> findAll() {
        return getShelfRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getShelfRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Shelf> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getShelfRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Shelf> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getShelfRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Shelf> findByFacility(Facility facility, GlobalSearch globalSearch, Pageable pageable) {
        return getShelfRepository().findByFacility(facility, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @return Long
     */
    public long countByFacility(Facility facility) {
        return getShelfRepository().countByFacility(facility);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<Shelf> getEntityType() {
        return Shelf.class;
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
