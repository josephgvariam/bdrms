package in.bigdash.rms.repository.shelf;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.Shelf;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.Facility;
import in.bigdash.rms.model.QShelf;
import io.springlets.data.domain.GlobalSearch;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Transactional(readOnly = true)
public class ShelfRepositoryImpl extends QueryDslRepositorySupportExt<Shelf> implements ShelfRepositoryCustom {


    ShelfRepositoryImpl() {
        super(Shelf.class);
    }


    public static final String STATUS = "status";


    public static final String FACILITY = "facility";


    public static final String BARCODE = "barcode";


    public Page<Shelf> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QShelf shelf = QShelf.shelf;
        JPQLQuery<Shelf> query = from(shelf);
        Path<?>[] paths = new Path<?>[] { shelf.barcode, shelf.status, shelf.facility };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, shelf.barcode).map(STATUS, shelf.status).map(FACILITY, shelf.facility);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, shelf);
    }


    public Page<Shelf> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QShelf shelf = QShelf.shelf;
        JPQLQuery<Shelf> query = from(shelf);
        Path<?>[] paths = new Path<?>[] { shelf.barcode, shelf.status, shelf.facility };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(shelf.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, shelf.barcode).map(STATUS, shelf.status).map(FACILITY, shelf.facility);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, shelf);
    }


    public Page<Shelf> findByFacility(Facility facility, GlobalSearch globalSearch, Pageable pageable) {
        QShelf shelf = QShelf.shelf;
        JPQLQuery<Shelf> query = from(shelf);
        Assert.notNull(facility, "facility is required");
        query.where(shelf.facility.eq(facility));
        Path<?>[] paths = new Path<?>[] { shelf.barcode, shelf.status, shelf.facility };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, shelf.barcode).map(STATUS, shelf.status).map(FACILITY, shelf.facility);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, shelf);
    }
}
