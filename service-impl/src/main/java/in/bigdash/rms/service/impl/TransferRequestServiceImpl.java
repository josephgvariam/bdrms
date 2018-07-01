package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.TransferRequestService;
import in.bigdash.rms.model.request.TransferRequest;
import in.bigdash.rms.repository.request.TransferRequestRepository;
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
public class TransferRequestServiceImpl implements TransferRequestService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private TransferRequestRepository transferRequestRepository;


    @Autowired
    public TransferRequestServiceImpl(TransferRequestRepository transferRequestRepository) {
        setTransferRequestRepository(transferRequestRepository);
    }


    public TransferRequestRepository getTransferRequestRepository() {
        return transferRequestRepository;
    }


    public void setTransferRequestRepository(TransferRequestRepository transferRequestRepository) {
        this.transferRequestRepository = transferRequestRepository;
    }


    public Map<String, List<MessageI18n>> validate(TransferRequest transferrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }


    @Transactional
    public void delete(TransferRequest transferRequest) {
        getTransferRequestRepository().delete(transferRequest);
    }


    @Transactional
    public List<TransferRequest> save(Iterable<TransferRequest> entities) {
        return getTransferRequestRepository().save(entities);
    }


    @Transactional
    public void delete(Iterable<Long> ids) {
        List<TransferRequest> toDelete = getTransferRequestRepository().findAll(ids);
        getTransferRequestRepository().deleteInBatch(toDelete);
    }


    @Transactional
    public TransferRequest save(TransferRequest entity) {
        return getTransferRequestRepository().save(entity);
    }


    public TransferRequest findOne(Long id) {
        return getTransferRequestRepository().findOne(id);
    }


    public TransferRequest findOneForUpdate(Long id) {
        return getTransferRequestRepository().findOneDetached(id);
    }


    public List<TransferRequest> findAll(Iterable<Long> ids) {
        return getTransferRequestRepository().findAll(ids);
    }


    public List<TransferRequest> findAll() {
        return getTransferRequestRepository().findAll();
    }


    public long count() {
        return getTransferRequestRepository().count();
    }


    public Page<TransferRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getTransferRequestRepository().findAll(globalSearch, pageable);
    }


    public Page<TransferRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getTransferRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }


    public Class<TransferRequest> getEntityType() {
        return TransferRequest.class;
    }


    public Class<Long> getIdType() {
        return Long.class;
    }
}
