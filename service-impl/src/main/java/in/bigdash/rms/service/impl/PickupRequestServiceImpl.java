package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.PickupRequestService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.request.PickupRequest;
import in.bigdash.rms.repository.PickupRequestRepository;
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
 * = PickupRequestServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = PickupRequestService.class)
@Service
@Transactional(readOnly = true)
public class PickupRequestServiceImpl implements PickupRequestService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private PickupRequestRepository pickupRequestRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param pickupRequestRepository
     */
    @Autowired
    public PickupRequestServiceImpl(PickupRequestRepository pickupRequestRepository) {
        setPickupRequestRepository(pickupRequestRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return PickupRequestRepository
     */
    public PickupRequestRepository getPickupRequestRepository() {
        return pickupRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param pickupRequestRepository
     */
    public void setPickupRequestRepository(PickupRequestRepository pickupRequestRepository) {
        this.pickupRequestRepository = pickupRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param pickuprequest
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(PickupRequest pickuprequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param pickupRequest
     */
    @Transactional
    public void delete(PickupRequest pickupRequest) {
        getPickupRequestRepository().delete(pickupRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<PickupRequest> save(Iterable<PickupRequest> entities) {
        return getPickupRequestRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<PickupRequest> toDelete = getPickupRequestRepository().findAll(ids);
        getPickupRequestRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return PickupRequest
     */
    @Transactional
    public PickupRequest save(PickupRequest entity) {
        return getPickupRequestRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return PickupRequest
     */
    public PickupRequest findOne(Long id) {
        return getPickupRequestRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return PickupRequest
     */
    public PickupRequest findOneForUpdate(Long id) {
        return getPickupRequestRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<PickupRequest> findAll(Iterable<Long> ids) {
        return getPickupRequestRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<PickupRequest> findAll() {
        return getPickupRequestRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getPickupRequestRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<PickupRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getPickupRequestRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<PickupRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getPickupRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<PickupRequest> getEntityType() {
        return PickupRequest.class;
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
