package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.PermoutRequestService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.request.PermoutRequest;
import in.bigdash.rms.repository.PermoutRequestRepository;
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
 * = PermoutRequestServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = PermoutRequestService.class)
@Service
@Transactional(readOnly = true)
public class PermoutRequestServiceImpl implements PermoutRequestService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private PermoutRequestRepository permoutRequestRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param permoutRequestRepository
     */
    @Autowired
    public PermoutRequestServiceImpl(PermoutRequestRepository permoutRequestRepository) {
        setPermoutRequestRepository(permoutRequestRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return PermoutRequestRepository
     */
    public PermoutRequestRepository getPermoutRequestRepository() {
        return permoutRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param permoutRequestRepository
     */
    public void setPermoutRequestRepository(PermoutRequestRepository permoutRequestRepository) {
        this.permoutRequestRepository = permoutRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param permoutrequest
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(PermoutRequest permoutrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param permoutRequest
     */
    @Transactional
    public void delete(PermoutRequest permoutRequest) {
        getPermoutRequestRepository().delete(permoutRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<PermoutRequest> save(Iterable<PermoutRequest> entities) {
        return getPermoutRequestRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<PermoutRequest> toDelete = getPermoutRequestRepository().findAll(ids);
        getPermoutRequestRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return PermoutRequest
     */
    @Transactional
    public PermoutRequest save(PermoutRequest entity) {
        return getPermoutRequestRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return PermoutRequest
     */
    public PermoutRequest findOne(Long id) {
        return getPermoutRequestRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return PermoutRequest
     */
    public PermoutRequest findOneForUpdate(Long id) {
        return getPermoutRequestRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<PermoutRequest> findAll(Iterable<Long> ids) {
        return getPermoutRequestRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<PermoutRequest> findAll() {
        return getPermoutRequestRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getPermoutRequestRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<PermoutRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getPermoutRequestRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<PermoutRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getPermoutRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<PermoutRequest> getEntityType() {
        return PermoutRequest.class;
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
