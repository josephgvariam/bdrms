package in.bigdash.rms.repository.request;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.request.TransferRequest;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.request.QTransferRequest;
import io.springlets.data.domain.GlobalSearch;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public class TransferRequestRepositoryImpl extends QueryDslRepositorySupportExt<TransferRequest> implements TransferRequestRepositoryCustom {


    TransferRequestRepositoryImpl() {
        super(TransferRequest.class);
    }


    public static final String CREATED_BY = "createdBy";


    public static final String USER_ASSIGNED = "userAssigned";


    public static final String NOTES = "notes";


    public static final String STATUS = "status";


    public static final String MODIFIED_BY = "modifiedBy";


    public static final String CREATED_DATE = "createdDate";


    public static final String USER_CREATED = "userCreated";


    public static final String STORAGE_TYPE = "storageType";


    public static final String MODIFIED_DATE = "modifiedDate";


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
