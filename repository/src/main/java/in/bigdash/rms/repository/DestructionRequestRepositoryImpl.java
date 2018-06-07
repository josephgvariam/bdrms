package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.request.DestructionRequest;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.request.QDestructionRequest;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepositoryCustomImpl(repository = DestructionRequestRepositoryCustom.class)
@Transactional(readOnly = true)
public class DestructionRequestRepositoryImpl extends QueryDslRepositorySupportExt<DestructionRequest> implements DestructionRequestRepositoryCustom {


    DestructionRequestRepositoryImpl() {
        super(DestructionRequest.class);
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


    public Page<DestructionRequest> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QDestructionRequest destructionRequest = QDestructionRequest.destructionRequest;
        JPQLQuery<DestructionRequest> query = from(destructionRequest);
        Path<?>[] paths = new Path<?>[] { destructionRequest.userCreated, destructionRequest.userAssigned, destructionRequest.storageType, destructionRequest.status, destructionRequest.notes, destructionRequest.createdDate, destructionRequest.createdBy, destructionRequest.modifiedDate, destructionRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, destructionRequest.userCreated).map(USER_ASSIGNED, destructionRequest.userAssigned).map(STORAGE_TYPE, destructionRequest.storageType).map(STATUS, destructionRequest.status).map(NOTES, destructionRequest.notes).map(CREATED_DATE, destructionRequest.createdDate).map(CREATED_BY, destructionRequest.createdBy).map(MODIFIED_DATE, destructionRequest.modifiedDate).map(MODIFIED_BY, destructionRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, destructionRequest);
    }


    public Page<DestructionRequest> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QDestructionRequest destructionRequest = QDestructionRequest.destructionRequest;
        JPQLQuery<DestructionRequest> query = from(destructionRequest);
        Path<?>[] paths = new Path<?>[] { destructionRequest.userCreated, destructionRequest.userAssigned, destructionRequest.storageType, destructionRequest.status, destructionRequest.notes, destructionRequest.createdDate, destructionRequest.createdBy, destructionRequest.modifiedDate, destructionRequest.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(destructionRequest.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(USER_CREATED, destructionRequest.userCreated).map(USER_ASSIGNED, destructionRequest.userAssigned).map(STORAGE_TYPE, destructionRequest.storageType).map(STATUS, destructionRequest.status).map(NOTES, destructionRequest.notes).map(CREATED_DATE, destructionRequest.createdDate).map(CREATED_BY, destructionRequest.createdBy).map(MODIFIED_DATE, destructionRequest.modifiedDate).map(MODIFIED_BY, destructionRequest.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, destructionRequest);
    }
}
