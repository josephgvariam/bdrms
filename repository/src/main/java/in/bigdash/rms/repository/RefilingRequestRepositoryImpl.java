package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
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

/**
 * = RefilingRequestRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = RefilingRequestRepositoryCustom.class)
@Transactional(readOnly = true)
public class RefilingRequestRepositoryImpl extends QueryDslRepositorySupportExt<RefilingRequest> implements RefilingRequestRepositoryCustom {

    /**
     * Default constructor
     */
    RefilingRequestRepositoryImpl() {
        super(RefilingRequest.class);
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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
