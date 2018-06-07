package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.DestructionRequestService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.request.DestructionRequest;
import in.bigdash.rms.repository.DestructionRequestRepository;
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
 * = DestructionRequestServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = DestructionRequestService.class)
@Service
@Transactional(readOnly = true)
public class DestructionRequestServiceImpl implements DestructionRequestService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private DestructionRequestRepository destructionRequestRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param destructionRequestRepository
     */
    @Autowired
    public DestructionRequestServiceImpl(DestructionRequestRepository destructionRequestRepository) {
        setDestructionRequestRepository(destructionRequestRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return DestructionRequestRepository
     */
    public DestructionRequestRepository getDestructionRequestRepository() {
        return destructionRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequestRepository
     */
    public void setDestructionRequestRepository(DestructionRequestRepository destructionRequestRepository) {
        this.destructionRequestRepository = destructionRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionrequest
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(DestructionRequest destructionrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param destructionRequest
     */
    @Transactional
    public void delete(DestructionRequest destructionRequest) {
        getDestructionRequestRepository().delete(destructionRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<DestructionRequest> save(Iterable<DestructionRequest> entities) {
        return getDestructionRequestRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<DestructionRequest> toDelete = getDestructionRequestRepository().findAll(ids);
        getDestructionRequestRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return DestructionRequest
     */
    @Transactional
    public DestructionRequest save(DestructionRequest entity) {
        return getDestructionRequestRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return DestructionRequest
     */
    public DestructionRequest findOne(Long id) {
        return getDestructionRequestRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return DestructionRequest
     */
    public DestructionRequest findOneForUpdate(Long id) {
        return getDestructionRequestRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<DestructionRequest> findAll(Iterable<Long> ids) {
        return getDestructionRequestRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<DestructionRequest> findAll() {
        return getDestructionRequestRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getDestructionRequestRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<DestructionRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getDestructionRequestRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<DestructionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getDestructionRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<DestructionRequest> getEntityType() {
        return DestructionRequest.class;
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
