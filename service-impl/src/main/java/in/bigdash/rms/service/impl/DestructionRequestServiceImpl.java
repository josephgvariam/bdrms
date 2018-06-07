package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.DestructionRequestService;
import in.bigdash.rms.model.request.DestructionRequest;
import in.bigdash.rms.repository.request.DestructionRequestRepository;
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
public class DestructionRequestServiceImpl implements DestructionRequestService {


    private DestructionRequestRepository destructionRequestRepository;


    @Autowired
    public DestructionRequestServiceImpl(DestructionRequestRepository destructionRequestRepository) {
        setDestructionRequestRepository(destructionRequestRepository);
    }


    public DestructionRequestRepository getDestructionRequestRepository() {
        return destructionRequestRepository;
    }


    public void setDestructionRequestRepository(DestructionRequestRepository destructionRequestRepository) {
        this.destructionRequestRepository = destructionRequestRepository;
    }


    public Map<String, List<MessageI18n>> validate(DestructionRequest destructionrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(DestructionRequest destructionRequest) {
        getDestructionRequestRepository().delete(destructionRequest);
    }


    @Transactional
    public List<DestructionRequest> save(Iterable<DestructionRequest> entities) {
        return getDestructionRequestRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<DestructionRequest> toDelete = getDestructionRequestRepository().findAll(ids);
        getDestructionRequestRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public DestructionRequest save(DestructionRequest entity) {
        return getDestructionRequestRepository().save(entity);
    }


    public DestructionRequest findOne(Long id) {
        return getDestructionRequestRepository().findOne(id);
    }


    public DestructionRequest findOneForUpdate(Long id) {
        return getDestructionRequestRepository().findOneDetached(id);
    }


    public List<DestructionRequest> findAll(Iterable<Long> ids) {
        return getDestructionRequestRepository().findAll(ids);
    }


    public List<DestructionRequest> findAll() {
        return getDestructionRequestRepository().findAll();
    }


    public long count() {
        return getDestructionRequestRepository().count();
    }


    public Page<DestructionRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getDestructionRequestRepository().findAll(globalSearch, pageable);
    }


    public Page<DestructionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getDestructionRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Class<DestructionRequest> getEntityType() {
        return DestructionRequest.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
