package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.RefilingRequestService;
import in.bigdash.rms.model.request.RefilingRequest;
import in.bigdash.rms.repository.request.RefilingRequestRepository;
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
public class RefilingRequestServiceImpl implements RefilingRequestService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private RefilingRequestRepository refilingRequestRepository;


    @Autowired
    public RefilingRequestServiceImpl(RefilingRequestRepository refilingRequestRepository) {
        setRefilingRequestRepository(refilingRequestRepository);
    }


    public RefilingRequestRepository getRefilingRequestRepository() {
        return refilingRequestRepository;
    }


    public void setRefilingRequestRepository(RefilingRequestRepository refilingRequestRepository) {
        this.refilingRequestRepository = refilingRequestRepository;
    }


    public Map<String, List<MessageI18n>> validate(RefilingRequest refilingrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(RefilingRequest refilingRequest) {
        getRefilingRequestRepository().delete(refilingRequest);
    }


    @Transactional
    public List<RefilingRequest> save(Iterable<RefilingRequest> entities) {
        return getRefilingRequestRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<RefilingRequest> toDelete = getRefilingRequestRepository().findAll(ids);
        getRefilingRequestRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public RefilingRequest save(RefilingRequest entity) {
        return getRefilingRequestRepository().save(entity);
    }


    public RefilingRequest findOne(Long id) {
        return getRefilingRequestRepository().findOne(id);
    }


    public RefilingRequest findOneForUpdate(Long id) {
        return getRefilingRequestRepository().findOneDetached(id);
    }


    public List<RefilingRequest> findAll(Iterable<Long> ids) {
        return getRefilingRequestRepository().findAll(ids);
    }


    public List<RefilingRequest> findAll() {
        return getRefilingRequestRepository().findAll();
    }


    public long count() {
        return getRefilingRequestRepository().count();
    }


    public Page<RefilingRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getRefilingRequestRepository().findAll(globalSearch, pageable);
    }


    public Page<RefilingRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getRefilingRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Class<RefilingRequest> getEntityType() {
        return RefilingRequest.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
