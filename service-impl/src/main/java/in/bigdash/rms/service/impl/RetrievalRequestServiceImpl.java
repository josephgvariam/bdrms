package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.RetrievalRequestService;
import in.bigdash.rms.model.request.RetrievalRequest;
import in.bigdash.rms.repository.request.RetrievalRequestRepository;
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
public class RetrievalRequestServiceImpl implements RetrievalRequestService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private RetrievalRequestRepository retrievalRequestRepository;


    @Autowired
    public RetrievalRequestServiceImpl(RetrievalRequestRepository retrievalRequestRepository) {
        setRetrievalRequestRepository(retrievalRequestRepository);
    }


    public RetrievalRequestRepository getRetrievalRequestRepository() {
        return retrievalRequestRepository;
    }


    public void setRetrievalRequestRepository(RetrievalRequestRepository retrievalRequestRepository) {
        this.retrievalRequestRepository = retrievalRequestRepository;
    }


    public Map<String, List<MessageI18n>> validate(RetrievalRequest retrievalrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(RetrievalRequest retrievalRequest) {
        getRetrievalRequestRepository().delete(retrievalRequest);
    }


    @Transactional
    public List<RetrievalRequest> save(Iterable<RetrievalRequest> entities) {
        return getRetrievalRequestRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<RetrievalRequest> toDelete = getRetrievalRequestRepository().findAll(ids);
        getRetrievalRequestRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public RetrievalRequest save(RetrievalRequest entity) {
        return getRetrievalRequestRepository().save(entity);
    }


    public RetrievalRequest findOne(Long id) {
        return getRetrievalRequestRepository().findOne(id);
    }


    public RetrievalRequest findOneForUpdate(Long id) {
        return getRetrievalRequestRepository().findOneDetached(id);
    }


    public List<RetrievalRequest> findAll(Iterable<Long> ids) {
        return getRetrievalRequestRepository().findAll(ids);
    }


    public List<RetrievalRequest> findAll() {
        return getRetrievalRequestRepository().findAll();
    }


    public long count() {
        return getRetrievalRequestRepository().count();
    }


    public Page<RetrievalRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getRetrievalRequestRepository().findAll(globalSearch, pageable);
    }


    public Page<RetrievalRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getRetrievalRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Class<RetrievalRequest> getEntityType() {
        return RetrievalRequest.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
