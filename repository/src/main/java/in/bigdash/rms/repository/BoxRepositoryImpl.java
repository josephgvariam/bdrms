package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.Box;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.QBox;
import in.bigdash.rms.model.Shelf;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * = BoxRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = BoxRepositoryCustom.class)
@Transactional(readOnly = true)
public class BoxRepositoryImpl extends QueryDslRepositorySupportExt<Box> implements BoxRepositoryCustom {

    /**
     * Default constructor
     */
    BoxRepositoryImpl() {
        super(Box.class);
    }

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String LOCATION = "location";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String CREATED_BY = "createdBy";

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
    public static final String BARCODE = "barcode";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String SHELF = "shelf";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String MODIFIED_DATE = "modifiedDate";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String INVENTORY_ITEM = "inventoryItem";

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Box> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QBox box = QBox.box;
        JPQLQuery<Box> query = from(box);
        Path<?>[] paths = new Path<?>[] { box.barcode, box.location, box.inventoryItem, box.shelf, box.createdDate, box.createdBy, box.modifiedDate, box.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, box.barcode).map(LOCATION, box.location).map(INVENTORY_ITEM, box.inventoryItem).map(SHELF, box.shelf).map(CREATED_DATE, box.createdDate).map(CREATED_BY, box.createdBy).map(MODIFIED_DATE, box.modifiedDate).map(MODIFIED_BY, box.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, box);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Box> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QBox box = QBox.box;
        JPQLQuery<Box> query = from(box);
        Path<?>[] paths = new Path<?>[] { box.barcode, box.location, box.inventoryItem, box.shelf, box.createdDate, box.createdBy, box.modifiedDate, box.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(box.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, box.barcode).map(LOCATION, box.location).map(INVENTORY_ITEM, box.inventoryItem).map(SHELF, box.shelf).map(CREATED_DATE, box.createdDate).map(CREATED_BY, box.createdBy).map(MODIFIED_DATE, box.modifiedDate).map(MODIFIED_BY, box.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, box);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param shelf
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Box> findByShelf(Shelf shelf, GlobalSearch globalSearch, Pageable pageable) {
        QBox box = QBox.box;
        JPQLQuery<Box> query = from(box);
        Assert.notNull(shelf, "shelf is required");
        query.where(box.shelf.eq(shelf));
        Path<?>[] paths = new Path<?>[] { box.barcode, box.location, box.inventoryItem, box.shelf, box.createdDate, box.createdBy, box.modifiedDate, box.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, box.barcode).map(LOCATION, box.location).map(INVENTORY_ITEM, box.inventoryItem).map(SHELF, box.shelf).map(CREATED_DATE, box.createdDate).map(CREATED_BY, box.createdBy).map(MODIFIED_DATE, box.modifiedDate).map(MODIFIED_BY, box.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, box);
    }
}
