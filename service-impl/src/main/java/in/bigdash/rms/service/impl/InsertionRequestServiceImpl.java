package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.InsertionRequestService;
import in.bigdash.rms.model.request.InsertionRequest;
import in.bigdash.rms.repository.request.InsertionRequestRepository;
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
public class InsertionRequestServiceImpl implements InsertionRequestService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private InsertionRequestRepository insertionRequestRepository;


    @Autowired
    public InsertionRequestServiceImpl(InsertionRequestRepository insertionRequestRepository) {
        setInsertionRequestRepository(insertionRequestRepository);
    }


    public InsertionRequestRepository getInsertionRequestRepository() {
        return insertionRequestRepository;
    }


    public void setInsertionRequestRepository(InsertionRequestRepository insertionRequestRepository) {
        this.insertionRequestRepository = insertionRequestRepository;
    }


    public Map<String, List<MessageI18n>> validate(InsertionRequest insertionrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(InsertionRequest insertionRequest) {
        getInsertionRequestRepository().delete(insertionRequest);
    }


    @Transactional
    public List<InsertionRequest> save(Iterable<InsertionRequest> entities) {
        return getInsertionRequestRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<InsertionRequest> toDelete = getInsertionRequestRepository().findAll(ids);
        getInsertionRequestRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public InsertionRequest save(InsertionRequest entity) {
        return getInsertionRequestRepository().save(entity);
    }


    public InsertionRequest findOne(Long id) {
        return getInsertionRequestRepository().findOne(id);
    }


    public InsertionRequest findOneForUpdate(Long id) {
        return getInsertionRequestRepository().findOneDetached(id);
    }


    public List<InsertionRequest> findAll(Iterable<Long> ids) {
        return getInsertionRequestRepository().findAll(ids);
    }


    public List<InsertionRequest> findAll() {
        return getInsertionRequestRepository().findAll();
    }


    public long count() {
        return getInsertionRequestRepository().count();
    }


    public Page<InsertionRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getInsertionRequestRepository().findAll(globalSearch, pageable);
    }


    public Page<InsertionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getInsertionRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Class<InsertionRequest> getEntityType() {
        return InsertionRequest.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
