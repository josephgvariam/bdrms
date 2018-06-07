package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
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

/**
 * = RequestRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = RequestRepositoryCustom.class)
@Transactional(readOnly = true)
public class RequestRepositoryImpl extends QueryDslRepositorySupportExt<Request> implements RequestRepositoryCustom {

    /**
     * Default constructor
     */
    RequestRepositoryImpl() {
        super(Request.class);
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageType
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param userAssigned
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param userCreated
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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
