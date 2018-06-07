package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.request.TransferRequest;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.request.QTransferRequest;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * = TransferRequestRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = TransferRequestRepositoryCustom.class)
@Transactional(readOnly = true)
public class TransferRequestRepositoryImpl extends QueryDslRepositorySupportExt<TransferRequest> implements TransferRequestRepositoryCustom {

    /**
     * Default constructor
     */
    TransferRequestRepositoryImpl() {
        super(TransferRequest.class);
    }

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String CREATED_BY = "createdBy";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String USER_ASSIGNED = "userAssigned";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String NOTES = "notes";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String STATUS = "status";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String MODIFIED_BY = "modifiedBy";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String CREATED_DATE = "createdDate";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String USER_CREATED = "userCreated";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String STORAGE_TYPE = "storageType";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String MODIFIED_DATE = "modifiedDate";

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<TransferRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QTransferRequest transferRequest = QTransferRequest.transferRequest;
        JPQLQuery<TransferRequest> query = from(transferRequest);
        Path<?>[] paths = new Path<?>[] { transferRequest.userCreated, transferRequest.userAssigned, transferRequest.storageType, transferRequest.status, transferRequest.notes, transferRequest.createdDate, transferRequest.createdBy, transferRequest.modifiedDate, transferRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, transferRequest.userCreated).map(USER_ASSIGNED, transferRequest.userAssigned).map(STORAGE_TYPE, transferRequest.storageType).map(STATUS, transferRequest.status).map(NOTES, transferRequest.notes).map(CREATED_DATE, transferRequest.createdDate).map(CREATED_BY, transferRequest.createdBy).map(MODIFIED_DATE, transferRequest.modifiedDate).map(MODIFIED_BY, transferRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, transferRequest);
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
        QTransferRequest transferRequest = QTransferRequest.transferRequest;
        JPQLQuery<TransferRequest> query = from(transferRequest);
        Path<?>[] paths = new Path<?>[] { transferRequest.userCreated, transferRequest.userAssigned, transferRequest.storageType, transferRequest.status, transferRequest.notes, transferRequest.createdDate, transferRequest.createdBy, transferRequest.modifiedDate, transferRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(transferRequest.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, transferRequest.userCreated).map(USER_ASSIGNED, transferRequest.userAssigned).map(STORAGE_TYPE, transferRequest.storageType).map(STATUS, transferRequest.status).map(NOTES, transferRequest.notes).map(CREATED_DATE, transferRequest.createdDate).map(CREATED_BY, transferRequest.createdBy).map(MODIFIED_DATE, transferRequest.modifiedDate).map(MODIFIED_BY, transferRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, transferRequest);
    }
}
