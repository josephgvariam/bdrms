package in.bigdash.rms.service.impl;
import in.bigdash.rms.service.api.TransferRequestService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import in.bigdash.rms.model.request.TransferRequest;
import in.bigdash.rms.repository.TransferRequestRepository;
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
 * = TransferRequestServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = TransferRequestService.class)
@Service
@Transactional(readOnly = true)
public class TransferRequestServiceImpl implements TransferRequestService {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private TransferRequestRepository transferRequestRepository;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param transferRequestRepository
     */
    @Autowired
    public TransferRequestServiceImpl(TransferRequestRepository transferRequestRepository) {
        setTransferRequestRepository(transferRequestRepository);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return TransferRequestRepository
     */
    public TransferRequestRepository getTransferRequestRepository() {
        return transferRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequestRepository
     */
    public void setTransferRequestRepository(TransferRequestRepository transferRequestRepository) {
        this.transferRequestRepository = transferRequestRepository;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferrequest
     * @return Map
     */
    public Map<String, List<MessageI18n>> validate(TransferRequest transferrequest) {
        Map<String, List<MessageI18n>> messages = new java.util.HashMap<String, List<MessageI18n>>();
        // TODO: IMPLEMENT HERE THE VALIDATION OF YOUR ENTITY
        return messages;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param transferRequest
     */
    @Transactional
    public void delete(TransferRequest transferRequest) {
        getTransferRequestRepository().delete(transferRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entities
     * @return List
     */
    @Transactional
    public List<TransferRequest> save(Iterable<TransferRequest> entities) {
        return getTransferRequestRepository().save(entities);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     */
    @Transactional
    public void delete(Iterable<Long> ids) {
        List<TransferRequest> toDelete = getTransferRequestRepository().findAll(ids);
        getTransferRequestRepository().deleteInBatch(toDelete);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param entity
     * @return TransferRequest
     */
    @Transactional
    public TransferRequest save(TransferRequest entity) {
        return getTransferRequestRepository().save(entity);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return TransferRequest
     */
    public TransferRequest findOne(Long id) {
        return getTransferRequestRepository().findOne(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param id
     * @return TransferRequest
     */
    public TransferRequest findOneForUpdate(Long id) {
        return getTransferRequestRepository().findOneDetached(id);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @return List
     */
    public List<TransferRequest> findAll(Iterable<Long> ids) {
        return getTransferRequestRepository().findAll(ids);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return List
     */
    public List<TransferRequest> findAll() {
        return getTransferRequestRepository().findAll();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Long
     */
    public long count() {
        return getTransferRequestRepository().count();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<TransferRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        return getTransferRequestRepository().findAll(globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<TransferRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        return getTransferRequestRepository().findAllByIdsIn(ids, globalSearch, pageable);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return Class
     */
    public Class<TransferRequest> getEntityType() {
        return TransferRequest.class;
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
