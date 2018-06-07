package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.Shelf;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.Facility;
import in.bigdash.rms.model.QShelf;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * = ShelfRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = ShelfRepositoryCustom.class)
@Transactional(readOnly = true)
public class ShelfRepositoryImpl extends QueryDslRepositorySupportExt<Shelf> implements ShelfRepositoryCustom {

    /**
     * Default constructor
     */
    ShelfRepositoryImpl() {
        super(Shelf.class);
    }

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String STATUS = "status";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String FACILITY = "facility";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String BARCODE = "barcode";

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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

    /**
     * TODO Auto-generated method documentation
     *
     * @param facility
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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
