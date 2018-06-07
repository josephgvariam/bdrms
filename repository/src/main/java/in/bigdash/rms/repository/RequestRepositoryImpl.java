package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.request.Request;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.StorageType;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.request.QRequest;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Transactional(readOnly = true)
public class RequestRepositoryImpl extends QueryDslRepositorySupportExt<Request> implements RequestRepositoryCustom {


    RequestRepositoryImpl() {
        super(Request.class);
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


    public Page<Request> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QRequest request = QRequest.request;
        JPQLQuery<Request> query = from(request);
        Path<?>[] paths = new Path<?>[] { request.userCreated, request.userAssigned, request.storageType, request.status, request.notes, request.createdDate, request.createdBy, request.modifiedDate, request.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, request.userCreated).map(USER_ASSIGNED, request.userAssigned).map(STORAGE_TYPE, request.storageType).map(STATUS, request.status).map(NOTES, request.notes).map(CREATED_DATE, request.createdDate).map(CREATED_BY, request.createdBy).map(MODIFIED_DATE, request.modifiedDate).map(MODIFIED_BY, request.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, request);
    }


    public Page<Request> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QRequest request = QRequest.request;
        JPQLQuery<Request> query = from(request);
        Path<?>[] paths = new Path<?>[] { request.userCreated, request.userAssigned, request.storageType, request.status, request.notes, request.createdDate, request.createdBy, request.modifiedDate, request.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(request.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, request.userCreated).map(USER_ASSIGNED, request.userAssigned).map(STORAGE_TYPE, request.storageType).map(STATUS, request.status).map(NOTES, request.notes).map(CREATED_DATE, request.createdDate).map(CREATED_BY, request.createdBy).map(MODIFIED_DATE, request.modifiedDate).map(MODIFIED_BY, request.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, request);
    }


    public Page<Request> findByStorageType(StorageType storageType, GlobalSearch globalSearch, Pageable pageable) {
        QRequest request = QRequest.request;
        JPQLQuery<Request> query = from(request);
        Assert.notNull(storageType, "storageType is required");
        query.where(request.storageType.eq(storageType));
        Path<?>[] paths = new Path<?>[] { request.userCreated, request.userAssigned, request.storageType, request.status, request.notes, request.createdDate, request.createdBy, request.modifiedDate, request.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, request.userCreated).map(USER_ASSIGNED, request.userAssigned).map(STORAGE_TYPE, request.storageType).map(STATUS, request.status).map(NOTES, request.notes).map(CREATED_DATE, request.createdDate).map(CREATED_BY, request.createdBy).map(MODIFIED_DATE, request.modifiedDate).map(MODIFIED_BY, request.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, request);
    }


    public Page<Request> findByUserAssigned(User userAssigned, GlobalSearch globalSearch, Pageable pageable) {
        QRequest request = QRequest.request;
        JPQLQuery<Request> query = from(request);
        Assert.notNull(userAssigned, "userAssigned is required");
        query.where(request.userAssigned.eq(userAssigned));
        Path<?>[] paths = new Path<?>[] { request.userCreated, request.userAssigned, request.storageType, request.status, request.notes, request.createdDate, request.createdBy, request.modifiedDate, request.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, request.userCreated).map(USER_ASSIGNED, request.userAssigned).map(STORAGE_TYPE, request.storageType).map(STATUS, request.status).map(NOTES, request.notes).map(CREATED_DATE, request.createdDate).map(CREATED_BY, request.createdBy).map(MODIFIED_DATE, request.modifiedDate).map(MODIFIED_BY, request.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, request);
    }


    public Page<Request> findByUserCreated(User userCreated, GlobalSearch globalSearch, Pageable pageable) {
        QRequest request = QRequest.request;
        JPQLQuery<Request> query = from(request);
        Assert.notNull(userCreated, "userCreated is required");
        query.where(request.userCreated.eq(userCreated));
        Path<?>[] paths = new Path<?>[] { request.userCreated, request.userAssigned, request.storageType, request.status, request.notes, request.createdDate, request.createdBy, request.modifiedDate, request.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, request.userCreated).map(USER_ASSIGNED, request.userAssigned).map(STORAGE_TYPE, request.storageType).map(STATUS, request.status).map(NOTES, request.notes).map(CREATED_DATE, request.createdDate).map(CREATED_BY, request.createdBy).map(MODIFIED_DATE, request.modifiedDate).map(MODIFIED_BY, request.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, request);
    }
}
