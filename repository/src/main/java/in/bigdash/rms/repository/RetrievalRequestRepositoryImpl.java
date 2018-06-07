package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.request.RetrievalRequest;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.request.QRetrievalRequest;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public class RetrievalRequestRepositoryImpl extends QueryDslRepositorySupportExt<RetrievalRequest> implements RetrievalRequestRepositoryCustom {


    RetrievalRequestRepositoryImpl() {
        super(RetrievalRequest.class);
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


    public Page<RetrievalRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QRetrievalRequest retrievalRequest = QRetrievalRequest.retrievalRequest;
        JPQLQuery<RetrievalRequest> query = from(retrievalRequest);
        Path<?>[] paths = new Path<?>[] { retrievalRequest.userCreated, retrievalRequest.userAssigned, retrievalRequest.storageType, retrievalRequest.status, retrievalRequest.notes, retrievalRequest.createdDate, retrievalRequest.createdBy, retrievalRequest.modifiedDate, retrievalRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, retrievalRequest.userCreated).map(USER_ASSIGNED, retrievalRequest.userAssigned).map(STORAGE_TYPE, retrievalRequest.storageType).map(STATUS, retrievalRequest.status).map(NOTES, retrievalRequest.notes).map(CREATED_DATE, retrievalRequest.createdDate).map(CREATED_BY, retrievalRequest.createdBy).map(MODIFIED_DATE, retrievalRequest.modifiedDate).map(MODIFIED_BY, retrievalRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, retrievalRequest);
    }


    public Page<RetrievalRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QRetrievalRequest retrievalRequest = QRetrievalRequest.retrievalRequest;
        JPQLQuery<RetrievalRequest> query = from(retrievalRequest);
        Path<?>[] paths = new Path<?>[] { retrievalRequest.userCreated, retrievalRequest.userAssigned, retrievalRequest.storageType, retrievalRequest.status, retrievalRequest.notes, retrievalRequest.createdDate, retrievalRequest.createdBy, retrievalRequest.modifiedDate, retrievalRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(retrievalRequest.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, retrievalRequest.userCreated).map(USER_ASSIGNED, retrievalRequest.userAssigned).map(STORAGE_TYPE, retrievalRequest.storageType).map(STATUS, retrievalRequest.status).map(NOTES, retrievalRequest.notes).map(CREATED_DATE, retrievalRequest.createdDate).map(CREATED_BY, retrievalRequest.createdBy).map(MODIFIED_DATE, retrievalRequest.modifiedDate).map(MODIFIED_BY, retrievalRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, retrievalRequest);
    }
}
