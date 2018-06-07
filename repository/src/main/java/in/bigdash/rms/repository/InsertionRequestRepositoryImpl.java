package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.request.InsertionRequest;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.request.QInsertionRequest;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepositoryCustomImpl(repository = InsertionRequestRepositoryCustom.class)
@Transactional(readOnly = true)
public class InsertionRequestRepositoryImpl extends QueryDslRepositorySupportExt<InsertionRequest> implements InsertionRequestRepositoryCustom {


    InsertionRequestRepositoryImpl() {
        super(InsertionRequest.class);
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


    public Page<InsertionRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QInsertionRequest insertionRequest = QInsertionRequest.insertionRequest;
        JPQLQuery<InsertionRequest> query = from(insertionRequest);
        Path<?>[] paths = new Path<?>[] { insertionRequest.userCreated, insertionRequest.userAssigned, insertionRequest.storageType, insertionRequest.status, insertionRequest.notes, insertionRequest.createdDate, insertionRequest.createdBy, insertionRequest.modifiedDate, insertionRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, insertionRequest.userCreated).map(USER_ASSIGNED, insertionRequest.userAssigned).map(STORAGE_TYPE, insertionRequest.storageType).map(STATUS, insertionRequest.status).map(NOTES, insertionRequest.notes).map(CREATED_DATE, insertionRequest.createdDate).map(CREATED_BY, insertionRequest.createdBy).map(MODIFIED_DATE, insertionRequest.modifiedDate).map(MODIFIED_BY, insertionRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, insertionRequest);
    }


    public Page<InsertionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QInsertionRequest insertionRequest = QInsertionRequest.insertionRequest;
        JPQLQuery<InsertionRequest> query = from(insertionRequest);
        Path<?>[] paths = new Path<?>[] { insertionRequest.userCreated, insertionRequest.userAssigned, insertionRequest.storageType, insertionRequest.status, insertionRequest.notes, insertionRequest.createdDate, insertionRequest.createdBy, insertionRequest.modifiedDate, insertionRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(insertionRequest.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, insertionRequest.userCreated).map(USER_ASSIGNED, insertionRequest.userAssigned).map(STORAGE_TYPE, insertionRequest.storageType).map(STATUS, insertionRequest.status).map(NOTES, insertionRequest.notes).map(CREATED_DATE, insertionRequest.createdDate).map(CREATED_BY, insertionRequest.createdBy).map(MODIFIED_DATE, insertionRequest.modifiedDate).map(MODIFIED_BY, insertionRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, insertionRequest);
    }
}
