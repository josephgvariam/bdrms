package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.RefilingRequestService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.request.RefilingRequest;
import in.bigdash.rms.repository.RefilingRequestRepository;
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
 * = RefilingRequestServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = RefilingRequestService.class)
@Service
@Transactional(readOnly = true)
public class RefilingRequestServiceImpl implements RefilingRequestService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RefilingRequestRepository refilingRequestRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param refilingRequestRepository
     */
    @Autowired
    public RefilingRequestServiceImpl(RefilingRequestRepository refilingRequestRepository) {
        setRefilingRequestRepository(refilingRequestRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RefilingRequestRepository
     */
    public RefilingRequestRepository getRefilingRequestRepository() {
        return refilingRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param refilingRequestRepository
     */
    public void setRefilingRequestRepository(RefilingRequestRepository refilingRequestRepository) {
        this.refilingRequestRepository = refilingRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param refilingrequest
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(RefilingRequest refilingrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param refilingRequest
     */
    @Transactional
    public void delete(RefilingRequest refilingRequest) {
        getRefilingRequestRepository().delete(refilingRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<RefilingRequest> save(Iterable<RefilingRequest> entities) {
        return getRefilingRequestRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<RefilingRequest> toDelete = getRefilingRequestRepository().findAll(ids);
        getRefilingRequestRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return RefilingRequest
     */
    @Transactional
    public RefilingRequest save(RefilingRequest entity) {
        return getRefilingRequestRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return RefilingRequest
     */
    public RefilingRequest findOne(Long id) {
        return getRefilingRequestRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return RefilingRequest
     */
    public RefilingRequest findOneForUpdate(Long id) {
        return getRefilingRequestRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<RefilingRequest> findAll(Iterable<Long> ids) {
        return getRefilingRequestRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<RefilingRequest> findAll() {
        return getRefilingRequestRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getRefilingRequestRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<RefilingRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getRefilingRequestRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<RefilingRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getRefilingRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<RefilingRequest> getEntityType() {
        return RefilingRequest.class;
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
