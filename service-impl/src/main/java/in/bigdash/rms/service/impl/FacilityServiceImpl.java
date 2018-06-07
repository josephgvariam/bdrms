package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.FacilityService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.Facility;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.repository.FacilityRepository;
import in.bigdash.rms.service.api.ShelfService;
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
 * = FacilityServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = FacilityService.class)
@Service
@Transactional(readOnly = true)
public class FacilityServiceImpl implements FacilityService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ShelfService shelfService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private FacilityRepository facilityRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param facilityRepository
     * @param shelfService
     */
    @Autowired
    public FacilityServiceImpl(FacilityRepository facilityRepository, @Lazy ShelfService shelfService) {
        setFacilityRepository(facilityRepository);
        setShelfService(shelfService);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return FacilityRepository
     */
    public FacilityRepository getFacilityRepository() {
        return facilityRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facilityRepository
     */
    public void setFacilityRepository(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ShelfService
     */
    public ShelfService getShelfService() {
        return shelfService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelfService
     */
    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(Facility facility) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param shelvesToAdd
     * @return Facility
     */
    @Transactional
    public Facility addToShelves(Facility facility, Iterable<Long> shelvesToAdd) {
        List<Shelf> shelves = getShelfService().findAll(shelvesToAdd);
        facility.addToShelves(shelves);
        return getFacilityRepository().save(facility);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param shelvesToRemove
     * @return Facility
     */
    @Transactional
    public Facility removeFromShelves(Facility facility, Iterable<Long> shelvesToRemove) {
        List<Shelf> shelves = getShelfService().findAll(shelvesToRemove);
        facility.removeFromShelves(shelves);
        return getFacilityRepository().save(facility);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param shelves
     * @return Facility
     */
    @Transactional
    public Facility setShelves(Facility facility, Iterable<Long> shelves) {
        List<Shelf> items = getShelfService().findAll(shelves);
        Set<Shelf> currents = facility.getShelves();
        Set<Shelf> toRemove = new HashSet<Shelf>();
        for (Iterator<Shelf> iterator = currents.iterator(); iterator.hasNext(); ) {
            Shelf nextShelf = iterator.next();
            if (items.contains(nextShelf)) {
                items.remove(nextShelf);
            } else {
                toRemove.add(nextShelf);
            }
        }
        facility.removeFromShelves(toRemove);
        facility.addToShelves(items);
        // Force the version update of the parent side to know that the parent has changed
        // because it has new books assigned
        facility.setVersion(facility.getVersion() + 1);
        return getFacilityRepository().save(facility);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     */
    @Transactional
    public void delete(Facility facility) {
        // Clear bidirectional one-to-many parent relationship with Shelf
        for (Shelf item : facility.getShelves()) {
            item.setFacility(null);
        }
        getFacilityRepository().delete(facility);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<Facility> save(Iterable<Facility> entities) {
        return getFacilityRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Facility> toDelete = getFacilityRepository().findAll(ids);
        getFacilityRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return Facility
     */
    @Transactional
    public Facility save(Facility entity) {
        return getFacilityRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Facility
     */
    public Facility findOne(Long id) {
        return getFacilityRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return Facility
     */
    public Facility findOneForUpdate(Long id) {
        return getFacilityRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<Facility> findAll(Iterable<Long> ids) {
        return getFacilityRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<Facility> findAll() {
        return getFacilityRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getFacilityRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Facility> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getFacilityRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Facility> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getFacilityRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<Facility> getEntityType() {
        return Facility.class;
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
