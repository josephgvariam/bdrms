package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.FacilityService;
import in.bigdash.rms.model.Facility;
import in.bigdash.rms.model.Shelf;
import in.bigdash.rms.repository.facility.FacilityRepository;
import in.bigdash.rms.service.api.ShelfService;
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
public class FacilityServiceImpl implements FacilityService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private ShelfService shelfService;


    private FacilityRepository facilityRepository;


    @Autowired
    public FacilityServiceImpl(FacilityRepository facilityRepository, @Lazy ShelfService shelfService) {
        setFacilityRepository(facilityRepository);
        setShelfService(shelfService);
    }


    public FacilityRepository getFacilityRepository() {
        return facilityRepository;
    }


    public void setFacilityRepository(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }


    public ShelfService getShelfService() {
        return shelfService;
    }


    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
    }


    public Map<String, List<MessageI18n>> validate(Facility facility) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public Facility addToShelves(Facility facility, Iterable<Long> shelvesToAdd) {
        List<Shelf> shelves = getShelfService().findAll(shelvesToAdd);
        facility.addToShelves(shelves);
        return getFacilityRepository().save(facility);
    }


    @Transactional
    public Facility removeFromShelves(Facility facility, Iterable<Long> shelvesToRemove) {
        List<Shelf> shelves = getShelfService().findAll(shelvesToRemove);
        facility.removeFromShelves(shelves);
        return getFacilityRepository().save(facility);
    }


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


    @Transactional
    public void delete(Facility facility) {
        // Clear bidirectional one-to-many parent relationship with Shelf
        for (Shelf item : facility.getShelves()) {
            item.setFacility(null);
        }
        getFacilityRepository().delete(facility);
    }


    @Transactional
    public List<Facility> save(Iterable<Facility> entities) {
        return getFacilityRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<Facility> toDelete = getFacilityRepository().findAll(ids);
        getFacilityRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public Facility save(Facility entity) {
        return getFacilityRepository().save(entity);
    }


    public Facility findOne(Long id) {
        return getFacilityRepository().findOne(id);
    }


    public Facility findOneForUpdate(Long id) {
        return getFacilityRepository().findOneDetached(id);
    }


    public List<Facility> findAll(Iterable<Long> ids) {
        return getFacilityRepository().findAll(ids);
    }


    public List<Facility> findAll() {
        return getFacilityRepository().findAll();
    }


    public long count() {
        return getFacilityRepository().count();
    }


    public Page<Facility> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getFacilityRepository().findAll(globalSearch, pageable);
    }


    public Page<Facility> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getFacilityRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Class<Facility> getEntityType() {
        return Facility.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
