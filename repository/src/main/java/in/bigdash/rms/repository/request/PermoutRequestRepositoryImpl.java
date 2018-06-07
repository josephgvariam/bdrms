package in.bigdash.rms.repository.request;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.request.PermoutRequest;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.request.QPermoutRequest;
import io.springlets.data.domain.GlobalSearch;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public class PermoutRequestRepositoryImpl extends QueryDslRepositorySupportExt<PermoutRequest> implements PermoutRequestRepositoryCustom {


    PermoutRequestRepositoryImpl() {
        super(PermoutRequest.class);
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


    public Page<PermoutRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QPermoutRequest permoutRequest = QPermoutRequest.permoutRequest;
        JPQLQuery<PermoutRequest> query = from(permoutRequest);
        Path<?>[] paths = new Path<?>[] { permoutRequest.userCreated, permoutRequest.userAssigned, permoutRequest.storageType, permoutRequest.status, permoutRequest.notes, permoutRequest.createdDate, permoutRequest.createdBy, permoutRequest.modifiedDate, permoutRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, permoutRequest.userCreated).map(USER_ASSIGNED, permoutRequest.userAssigned).map(STORAGE_TYPE, permoutRequest.storageType).map(STATUS, permoutRequest.status).map(NOTES, permoutRequest.notes).map(CREATED_DATE, permoutRequest.createdDate).map(CREATED_BY, permoutRequest.createdBy).map(MODIFIED_DATE, permoutRequest.modifiedDate).map(MODIFIED_BY, permoutRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, permoutRequest);
    }


    public Page<PermoutRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QPermoutRequest permoutRequest = QPermoutRequest.permoutRequest;
        JPQLQuery<PermoutRequest> query = from(permoutRequest);
        Path<?>[] paths = new Path<?>[] { permoutRequest.userCreated, permoutRequest.userAssigned, permoutRequest.storageType, permoutRequest.status, permoutRequest.notes, permoutRequest.createdDate, permoutRequest.createdBy, permoutRequest.modifiedDate, permoutRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(permoutRequest.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, permoutRequest.userCreated).map(USER_ASSIGNED, permoutRequest.userAssigned).map(STORAGE_TYPE, permoutRequest.storageType).map(STATUS, permoutRequest.status).map(NOTES, permoutRequest.notes).map(CREATED_DATE, permoutRequest.createdDate).map(CREATED_BY, permoutRequest.createdBy).map(MODIFIED_DATE, permoutRequest.modifiedDate).map(MODIFIED_BY, permoutRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, permoutRequest);
    }
}
