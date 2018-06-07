package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
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


@Transactional(readOnly = true)
public class BoxRepositoryImpl extends QueryDslRepositorySupportExt<Box> implements BoxRepositoryCustom {


    BoxRepositoryImpl() {
        super(Box.class);
    }


    public static final String LOCATION = "location";


    public static final String CREATED_BY = "createdBy";


    public static final String MODIFIED_BY = "modifiedBy";


    public static final String CREATED_DATE = "createdDate";


    public static final String BARCODE = "barcode";


    public static final String SHELF = "shelf";


    public static final String MODIFIED_DATE = "modifiedDate";


    public static final String INVENTORY_ITEM = "inventoryItem";


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
