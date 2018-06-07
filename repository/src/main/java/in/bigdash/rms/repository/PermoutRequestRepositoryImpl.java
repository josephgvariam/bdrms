package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.request.PermoutRequest;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.request.QPermoutRequest;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * = PermoutRequestRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = PermoutRequestRepositoryCustom.class)
@Transactional(readOnly = true)
public class PermoutRequestRepositoryImpl extends QueryDslRepositorySupportExt<PermoutRequest> implements PermoutRequestRepositoryCustom {

    /**
     * Default constructor
     */
    PermoutRequestRepositoryImpl() {
        super(PermoutRequest.class);
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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
