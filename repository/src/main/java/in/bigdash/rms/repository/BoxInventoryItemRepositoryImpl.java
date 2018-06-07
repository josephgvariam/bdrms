package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
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


@Transactional(readOnly = true)
public class BoxInventoryItemRepositoryImpl extends QueryDslRepositorySupportExt<BoxInventoryItem> implements BoxInventoryItemRepositoryCustom {


    BoxInventoryItemRepositoryImpl() {
        super(BoxInventoryItem.class);
    }


    public static final String STATUS = "status";


    public static final String REF_3 = "ref3";


    public static final String REF_2 = "ref2";


    public static final String REF_1 = "ref1";


    public static final String REF_5 = "ref5";


    public static final String BOX = "box";


    public static final String REF_4 = "ref4";


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
