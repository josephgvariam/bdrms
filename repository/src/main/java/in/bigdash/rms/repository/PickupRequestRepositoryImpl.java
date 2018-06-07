package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.request.PickupRequest;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.request.QPickupRequest;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * = PickupRequestRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = PickupRequestRepositoryCustom.class)
@Transactional(readOnly = true)
public class PickupRequestRepositoryImpl extends QueryDslRepositorySupportExt<PickupRequest> implements PickupRequestRepositoryCustom {

    /**
     * Default constructor
     */
    PickupRequestRepositoryImpl() {
        super(PickupRequest.class);
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
    public static final String PICKUP_DATE_TIME = "pickupDateTime";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String CREATED_DATE = "createdDate";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String NUMBER_FILES = "numberFiles";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String USER_CREATED = "userCreated";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String DOCUMENT_TYPE = "documentType";

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
    public Page<PickupRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QPickupRequest pickupRequest = QPickupRequest.pickupRequest;
        JPQLQuery<PickupRequest> query = from(pickupRequest);
        Path<?>[] paths = new Path<?>[] { pickupRequest.userCreated, pickupRequest.userAssigned, pickupRequest.storageType, pickupRequest.status, pickupRequest.notes, pickupRequest.createdDate, pickupRequest.createdBy, pickupRequest.modifiedDate, pickupRequest.modifiedBy, pickupRequest.documentType, pickupRequest.pickupDateTime, pickupRequest.numberFiles };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, pickupRequest.userCreated).map(USER_ASSIGNED, pickupRequest.userAssigned).map(STORAGE_TYPE, pickupRequest.storageType).map(STATUS, pickupRequest.status).map(NOTES, pickupRequest.notes).map(CREATED_DATE, pickupRequest.createdDate).map(CREATED_BY, pickupRequest.createdBy).map(MODIFIED_DATE, pickupRequest.modifiedDate).map(MODIFIED_BY, pickupRequest.modifiedBy).map(DOCUMENT_TYPE, pickupRequest.documentType).map(PICKUP_DATE_TIME, pickupRequest.pickupDateTime).map(NUMBER_FILES, pickupRequest.numberFiles);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, pickupRequest);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<PickupRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QPickupRequest pickupRequest = QPickupRequest.pickupRequest;
        JPQLQuery<PickupRequest> query = from(pickupRequest);
        Path<?>[] paths = new Path<?>[] { pickupRequest.userCreated, pickupRequest.userAssigned, pickupRequest.storageType, pickupRequest.status, pickupRequest.notes, pickupRequest.createdDate, pickupRequest.createdBy, pickupRequest.modifiedDate, pickupRequest.modifiedBy, pickupRequest.documentType, pickupRequest.pickupDateTime, pickupRequest.numberFiles };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(pickupRequest.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, pickupRequest.userCreated).map(USER_ASSIGNED, pickupRequest.userAssigned).map(STORAGE_TYPE, pickupRequest.storageType).map(STATUS, pickupRequest.status).map(NOTES, pickupRequest.notes).map(CREATED_DATE, pickupRequest.createdDate).map(CREATED_BY, pickupRequest.createdBy).map(MODIFIED_DATE, pickupRequest.modifiedDate).map(MODIFIED_BY, pickupRequest.modifiedBy).map(DOCUMENT_TYPE, pickupRequest.documentType).map(PICKUP_DATE_TIME, pickupRequest.pickupDateTime).map(NUMBER_FILES, pickupRequest.numberFiles);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, pickupRequest);
    }
}
