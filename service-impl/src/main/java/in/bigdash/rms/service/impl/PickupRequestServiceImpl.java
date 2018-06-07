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


@RooServiceImpl(service = PickupRequestService.class)
@Service
@Transactional(readOnly = true)
public class PickupRequestServiceImpl implements PickupRequestService {


    private PickupRequestRepository pickupRequestRepository;


    @Autowired
    public PickupRequestServiceImpl(PickupRequestRepository pickupRequestRepository) {
        setPickupRequestRepository(pickupRequestRepository);
    }


    public PickupRequestRepository getPickupRequestRepository() {
        return pickupRequestRepository;
    }


    public void setPickupRequestRepository(PickupRequestRepository pickupRequestRepository) {
        this.pickupRequestRepository = pickupRequestRepository;
    }


    public Map<String, List<MessageI18n>> validate(PickupRequest pickuprequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(PickupRequest pickupRequest) {
        getPickupRequestRepository().delete(pickupRequest);
    }


    @Transactional
    public List<PickupRequest> save(Iterable<PickupRequest> entities) {
        return getPickupRequestRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<PickupRequest> toDelete = getPickupRequestRepository().findAll(ids);
        getPickupRequestRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public PickupRequest save(PickupRequest entity) {
        return getPickupRequestRepository().save(entity);
    }


    public PickupRequest findOne(Long id) {
        return getPickupRequestRepository().findOne(id);
    }


    public PickupRequest findOneForUpdate(Long id) {
        return getPickupRequestRepository().findOneDetached(id);
    }


    public List<PickupRequest> findAll(Iterable<Long> ids) {
        return getPickupRequestRepository().findAll(ids);
    }


    public List<PickupRequest> findAll() {
        return getPickupRequestRepository().findAll();
    }


    public long count() {
        return getPickupRequestRepository().count();
    }


    public Page<PickupRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getPickupRequestRepository().findAll(globalSearch, pageable);
    }


    public Page<PickupRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getPickupRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Class<PickupRequest> getEntityType() {
        return PickupRequest.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
