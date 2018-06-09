package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.PermoutRequestService;
import in.bigdash.rms.model.request.PermoutRequest;
import in.bigdash.rms.repository.request.PermoutRequestRepository;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.web.validation.MessageI18n;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class PermoutRequestServiceImpl implements PermoutRequestService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private PermoutRequestRepository permoutRequestRepository;


    @Autowired
    public PermoutRequestServiceImpl(PermoutRequestRepository permoutRequestRepository) {
        setPermoutRequestRepository(permoutRequestRepository);
    }


    public PermoutRequestRepository getPermoutRequestRepository() {
        return permoutRequestRepository;
    }


    public void setPermoutRequestRepository(PermoutRequestRepository permoutRequestRepository) {
        this.permoutRequestRepository = permoutRequestRepository;
    }


    public Map<String, List<MessageI18n>> validate(PermoutRequest permoutrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(PermoutRequest permoutRequest) {
        getPermoutRequestRepository().delete(permoutRequest);
    }


    @Transactional
    public List<PermoutRequest> save(Iterable<PermoutRequest> entities) {
        return getPermoutRequestRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<PermoutRequest> toDelete = getPermoutRequestRepository().findAll(ids);
        getPermoutRequestRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public PermoutRequest save(PermoutRequest entity) {
        return getPermoutRequestRepository().save(entity);
    }


    public PermoutRequest findOne(Long id) {
        return getPermoutRequestRepository().findOne(id);
    }


    public PermoutRequest findOneForUpdate(Long id) {
        return getPermoutRequestRepository().findOneDetached(id);
    }


    public List<PermoutRequest> findAll(Iterable<Long> ids) {
        return getPermoutRequestRepository().findAll(ids);
    }


    public List<PermoutRequest> findAll() {
        return getPermoutRequestRepository().findAll();
    }


    public long count() {
        return getPermoutRequestRepository().count();
    }


    public Page<PermoutRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getPermoutRequestRepository().findAll(globalSearch, pageable);
    }


    public Page<PermoutRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getPermoutRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Class<PermoutRequest> getEntityType() {
        return PermoutRequest.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
