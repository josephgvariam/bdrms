package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.RetrievalRequestService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.request.RetrievalRequest;
import in.bigdash.rms.repository.RetrievalRequestRepository;
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
 * = RetrievalRequestServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = RetrievalRequestService.class)
@Service
@Transactional(readOnly = true)
public class RetrievalRequestServiceImpl implements RetrievalRequestService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RetrievalRequestRepository retrievalRequestRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param retrievalRequestRepository
     */
    @Autowired
    public RetrievalRequestServiceImpl(RetrievalRequestRepository retrievalRequestRepository) {
        setRetrievalRequestRepository(retrievalRequestRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RetrievalRequestRepository
     */
    public RetrievalRequestRepository getRetrievalRequestRepository() {
        return retrievalRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequestRepository
     */
    public void setRetrievalRequestRepository(RetrievalRequestRepository retrievalRequestRepository) {
        this.retrievalRequestRepository = retrievalRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalrequest
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(RetrievalRequest retrievalrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param retrievalRequest
     */
    @Transactional
    public void delete(RetrievalRequest retrievalRequest) {
        getRetrievalRequestRepository().delete(retrievalRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<RetrievalRequest> save(Iterable<RetrievalRequest> entities) {
        return getRetrievalRequestRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<RetrievalRequest> toDelete = getRetrievalRequestRepository().findAll(ids);
        getRetrievalRequestRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return RetrievalRequest
     */
    @Transactional
    public RetrievalRequest save(RetrievalRequest entity) {
        return getRetrievalRequestRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return RetrievalRequest
     */
    public RetrievalRequest findOne(Long id) {
        return getRetrievalRequestRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return RetrievalRequest
     */
    public RetrievalRequest findOneForUpdate(Long id) {
        return getRetrievalRequestRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<RetrievalRequest> findAll(Iterable<Long> ids) {
        return getRetrievalRequestRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<RetrievalRequest> findAll() {
        return getRetrievalRequestRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getRetrievalRequestRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<RetrievalRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getRetrievalRequestRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<RetrievalRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getRetrievalRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<RetrievalRequest> getEntityType() {
        return RetrievalRequest.class;
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
