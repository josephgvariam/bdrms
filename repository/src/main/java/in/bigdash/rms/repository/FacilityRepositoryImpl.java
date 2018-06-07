package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.Facility;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.QFacility;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepositoryCustomImpl(repository = FacilityRepositoryCustom.class)
@Transactional(readOnly = true)
public class FacilityRepositoryImpl extends QueryDslRepositorySupportExt<Facility> implements FacilityRepositoryCustom {


    FacilityRepositoryImpl() {
        super(Facility.class);
    }


    public static final String CREATED_BY = "createdBy";


    public static final String MODIFIED_BY = "modifiedBy";


    public static final String CREATED_DATE = "createdDate";


    public static final String ADDRESS = "address";


    public static final String MODIFIED_DATE = "modifiedDate";


    public static final String NAME = "name";


    public Page<Facility> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QFacility facility = QFacility.facility;
        JPQLQuery<Facility> query = from(facility);
        Path<?>[] paths = new Path<?>[] { facility.name, facility.address, facility.createdDate, facility.createdBy, facility.modifiedDate, facility.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(NAME, facility.name).map(ADDRESS, facility.address).map(CREATED_DATE, facility.createdDate).map(CREATED_BY, facility.createdBy).map(MODIFIED_DATE, facility.modifiedDate).map(MODIFIED_BY, facility.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, facility);
    }


    public Page<Facility> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QFacility facility = QFacility.facility;
        JPQLQuery<Facility> query = from(facility);
        Path<?>[] paths = new Path<?>[] { facility.name, facility.address, facility.createdDate, facility.createdBy, facility.modifiedDate, facility.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(facility.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(NAME, facility.name).map(ADDRESS, facility.address).map(CREATED_DATE, facility.createdDate).map(CREATED_BY, facility.createdBy).map(MODIFIED_DATE, facility.modifiedDate).map(MODIFIED_BY, facility.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, facility);
    }
}
