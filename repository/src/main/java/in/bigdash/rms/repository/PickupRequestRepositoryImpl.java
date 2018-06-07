package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
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


@Transactional(readOnly = true)
public class PickupRequestRepositoryImpl extends QueryDslRepositorySupportExt<PickupRequest> implements PickupRequestRepositoryCustom {


    PickupRequestRepositoryImpl() {
        super(PickupRequest.class);
    }


    public static final String CREATED_BY = "createdBy";


    public static final String USER_ASSIGNED = "userAssigned";


    public static final String NOTES = "notes";


    public static final String STATUS = "status";


    public static final String MODIFIED_BY = "modifiedBy";


    public static final String PICKUP_DATE_TIME = "pickupDateTime";


    public static final String CREATED_DATE = "createdDate";


    public static final String NUMBER_FILES = "numberFiles";


    public static final String USER_CREATED = "userCreated";


    public static final String DOCUMENT_TYPE = "documentType";


    public static final String STORAGE_TYPE = "storageType";


    public static final String MODIFIED_DATE = "modifiedDate";


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
