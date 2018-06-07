package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.InsertionRequestService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.request.InsertionRequest;
import in.bigdash.rms.repository.InsertionRequestRepository;
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
 * = InsertionRequestServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = InsertionRequestService.class)
@Service
@Transactional(readOnly = true)
public class InsertionRequestServiceImpl implements InsertionRequestService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private InsertionRequestRepository insertionRequestRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param insertionRequestRepository
     */
    @Autowired
    public InsertionRequestServiceImpl(InsertionRequestRepository insertionRequestRepository) {
        setInsertionRequestRepository(insertionRequestRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return InsertionRequestRepository
     */
    public InsertionRequestRepository getInsertionRequestRepository() {
        return insertionRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequestRepository
     */
    public void setInsertionRequestRepository(InsertionRequestRepository insertionRequestRepository) {
        this.insertionRequestRepository = insertionRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionrequest
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(InsertionRequest insertionrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param insertionRequest
     */
    @Transactional
    public void delete(InsertionRequest insertionRequest) {
        getInsertionRequestRepository().delete(insertionRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<InsertionRequest> save(Iterable<InsertionRequest> entities) {
        return getInsertionRequestRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<InsertionRequest> toDelete = getInsertionRequestRepository().findAll(ids);
        getInsertionRequestRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return InsertionRequest
     */
    @Transactional
    public InsertionRequest save(InsertionRequest entity) {
        return getInsertionRequestRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return InsertionRequest
     */
    public InsertionRequest findOne(Long id) {
        return getInsertionRequestRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return InsertionRequest
     */
    public InsertionRequest findOneForUpdate(Long id) {
        return getInsertionRequestRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<InsertionRequest> findAll(Iterable<Long> ids) {
        return getInsertionRequestRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<InsertionRequest> findAll() {
        return getInsertionRequestRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getInsertionRequestRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<InsertionRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getInsertionRequestRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<InsertionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getInsertionRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<InsertionRequest> getEntityType() {
        return InsertionRequest.class;
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
