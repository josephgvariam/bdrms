package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.inventory.InventoryItem;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.inventory.QInventoryItem;
import in.bigdash.rms.model.request.Request;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * = InventoryItemRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = InventoryItemRepositoryCustom.class)
@Transactional(readOnly = true)
public class InventoryItemRepositoryImpl extends QueryDslRepositorySupportExt<InventoryItem> implements InventoryItemRepositoryCustom {

    /**
     * Default constructor
     */
    InventoryItemRepositoryImpl() {
        super(InventoryItem.class);
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
    public static final String REF_3 = "ref3";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String REF_2 = "ref2";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String REF_1 = "ref1";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String REF_5 = "ref5";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String REF_4 = "ref4";

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<InventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QInventoryItem inventoryItem = QInventoryItem.inventoryItem;
        JPQLQuery<InventoryItem> query = from(inventoryItem);
        Path<?>[] paths = new Path<?>[] { inventoryItem.ref1, inventoryItem.ref2, inventoryItem.ref3, inventoryItem.ref4, inventoryItem.ref5, inventoryItem.status };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, inventoryItem.ref1).map(REF_2, inventoryItem.ref2).map(REF_3, inventoryItem.ref3).map(REF_4, inventoryItem.ref4).map(REF_5, inventoryItem.ref5).map(STATUS, inventoryItem.status);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, inventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<InventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QInventoryItem inventoryItem = QInventoryItem.inventoryItem;
        JPQLQuery<InventoryItem> query = from(inventoryItem);
        Path<?>[] paths = new Path<?>[] { inventoryItem.ref1, inventoryItem.ref2, inventoryItem.ref3, inventoryItem.ref4, inventoryItem.ref5, inventoryItem.status };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(inventoryItem.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, inventoryItem.ref1).map(REF_2, inventoryItem.ref2).map(REF_3, inventoryItem.ref3).map(REF_4, inventoryItem.ref4).map(REF_5, inventoryItem.ref5).map(STATUS, inventoryItem.status);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, inventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requests
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<InventoryItem> findByRequestsContains(Request requests, GlobalSearch globalSearch, Pageable pageable) {
        QInventoryItem inventoryItem = QInventoryItem.inventoryItem;
        JPQLQuery<InventoryItem> query = from(inventoryItem);
        Assert.notNull(requests, "requests is required");
        query.where(inventoryItem.requests.contains(requests));
        Path<?>[] paths = new Path<?>[] { inventoryItem.ref1, inventoryItem.ref2, inventoryItem.ref3, inventoryItem.ref4, inventoryItem.ref5, inventoryItem.status };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, inventoryItem.ref1).map(REF_2, inventoryItem.ref2).map(REF_3, inventoryItem.ref3).map(REF_4, inventoryItem.ref4).map(REF_5, inventoryItem.ref5).map(STATUS, inventoryItem.status);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, inventoryItem);
    }
}
