package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.inventory.QBoxInventoryItem;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * = BoxInventoryItemRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = BoxInventoryItemRepositoryCustom.class)
@Transactional(readOnly = true)
public class BoxInventoryItemRepositoryImpl extends QueryDslRepositorySupportExt<BoxInventoryItem> implements BoxInventoryItemRepositoryCustom {

    /**
     * Default constructor
     */
    BoxInventoryItemRepositoryImpl() {
        super(BoxInventoryItem.class);
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
    public static final String BOX = "box";

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
    public Page<BoxInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QBoxInventoryItem boxInventoryItem = QBoxInventoryItem.boxInventoryItem;
        JPQLQuery<BoxInventoryItem> query = from(boxInventoryItem);
        Path<?>[] paths = new Path<?>[] { boxInventoryItem.ref1, boxInventoryItem.ref2, boxInventoryItem.ref3, boxInventoryItem.ref4, boxInventoryItem.ref5, boxInventoryItem.status, boxInventoryItem.box };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, boxInventoryItem.ref1).map(REF_2, boxInventoryItem.ref2).map(REF_3, boxInventoryItem.ref3).map(REF_4, boxInventoryItem.ref4).map(REF_5, boxInventoryItem.ref5).map(STATUS, boxInventoryItem.status).map(BOX, boxInventoryItem.box);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, boxInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<BoxInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QBoxInventoryItem boxInventoryItem = QBoxInventoryItem.boxInventoryItem;
        JPQLQuery<BoxInventoryItem> query = from(boxInventoryItem);
        Path<?>[] paths = new Path<?>[] { boxInventoryItem.ref1, boxInventoryItem.ref2, boxInventoryItem.ref3, boxInventoryItem.ref4, boxInventoryItem.ref5, boxInventoryItem.status, boxInventoryItem.box };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(boxInventoryItem.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, boxInventoryItem.ref1).map(REF_2, boxInventoryItem.ref2).map(REF_3, boxInventoryItem.ref3).map(REF_4, boxInventoryItem.ref4).map(REF_5, boxInventoryItem.ref5).map(STATUS, boxInventoryItem.status).map(BOX, boxInventoryItem.box);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, boxInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<BoxInventoryItem> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable) {
        QBoxInventoryItem boxInventoryItem = QBoxInventoryItem.boxInventoryItem;
        JPQLQuery<BoxInventoryItem> query = from(boxInventoryItem);
        Assert.notNull(box, "box is required");
        query.where(boxInventoryItem.box.eq(box));
        Path<?>[] paths = new Path<?>[] { boxInventoryItem.ref1, boxInventoryItem.ref2, boxInventoryItem.ref3, boxInventoryItem.ref4, boxInventoryItem.ref5, boxInventoryItem.status, boxInventoryItem.box };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, boxInventoryItem.ref1).map(REF_2, boxInventoryItem.ref2).map(REF_3, boxInventoryItem.ref3).map(REF_4, boxInventoryItem.ref4).map(REF_5, boxInventoryItem.ref5).map(STATUS, boxInventoryItem.status).map(BOX, boxInventoryItem.box);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, boxInventoryItem);
    }
}
