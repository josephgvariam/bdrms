package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.request.RefilingRequest;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.request.QRefilingRequest;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public class RefilingRequestRepositoryImpl extends QueryDslRepositorySupportExt<RefilingRequest> implements RefilingRequestRepositoryCustom {


    RefilingRequestRepositoryImpl() {
        super(RefilingRequest.class);
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


    public Page<RefilingRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QRefilingRequest refilingRequest = QRefilingRequest.refilingRequest;
        JPQLQuery<RefilingRequest> query = from(refilingRequest);
        Path<?>[] paths = new Path<?>[] { refilingRequest.userCreated, refilingRequest.userAssigned, refilingRequest.storageType, refilingRequest.status, refilingRequest.notes, refilingRequest.createdDate, refilingRequest.createdBy, refilingRequest.modifiedDate, refilingRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, refilingRequest.userCreated).map(USER_ASSIGNED, refilingRequest.userAssigned).map(STORAGE_TYPE, refilingRequest.storageType).map(STATUS, refilingRequest.status).map(NOTES, refilingRequest.notes).map(CREATED_DATE, refilingRequest.createdDate).map(CREATED_BY, refilingRequest.createdBy).map(MODIFIED_DATE, refilingRequest.modifiedDate).map(MODIFIED_BY, refilingRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, refilingRequest);
    }


    public Page<RefilingRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QRefilingRequest refilingRequest = QRefilingRequest.refilingRequest;
        JPQLQuery<RefilingRequest> query = from(refilingRequest);
        Path<?>[] paths = new Path<?>[] { refilingRequest.userCreated, refilingRequest.userAssigned, refilingRequest.storageType, refilingRequest.status, refilingRequest.notes, refilingRequest.createdDate, refilingRequest.createdBy, refilingRequest.modifiedDate, refilingRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(refilingRequest.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, refilingRequest.userCreated).map(USER_ASSIGNED, refilingRequest.userAssigned).map(STORAGE_TYPE, refilingRequest.storageType).map(STATUS, refilingRequest.status).map(NOTES, refilingRequest.notes).map(CREATED_DATE, refilingRequest.createdDate).map(CREATED_BY, refilingRequest.createdBy).map(MODIFIED_DATE, refilingRequest.modifiedDate).map(MODIFIED_BY, refilingRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, refilingRequest);
    }
}
