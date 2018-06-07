package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.File;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.Box;
import in.bigdash.rms.model.QFile;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * = FileRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = FileRepositoryCustom.class)
@Transactional(readOnly = true)
public class FileRepositoryImpl extends QueryDslRepositorySupportExt<File> implements FileRepositoryCustom {

    /**
     * Default constructor
     */
    FileRepositoryImpl() {
        super(File.class);
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
    public static final String BOX = "box";

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
    public Page<File> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QFile file = QFile.file;
        JPQLQuery<File> query = from(file);
        Path<?>[] paths = new Path<?>[] { file.barcode, file.location, file.inventoryItem, file.box, file.createdDate, file.createdBy, file.modifiedDate, file.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, file.barcode).map(LOCATION, file.location).map(INVENTORY_ITEM, file.inventoryItem).map(BOX, file.box).map(CREATED_DATE, file.createdDate).map(CREATED_BY, file.createdBy).map(MODIFIED_DATE, file.modifiedDate).map(MODIFIED_BY, file.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, file);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<File> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QFile file = QFile.file;
        JPQLQuery<File> query = from(file);
        Path<?>[] paths = new Path<?>[] { file.barcode, file.location, file.inventoryItem, file.box, file.createdDate, file.createdBy, file.modifiedDate, file.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(file.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, file.barcode).map(LOCATION, file.location).map(INVENTORY_ITEM, file.inventoryItem).map(BOX, file.box).map(CREATED_DATE, file.createdDate).map(CREATED_BY, file.createdBy).map(MODIFIED_DATE, file.modifiedDate).map(MODIFIED_BY, file.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, file);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param box
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<File> findByBox(Box box, GlobalSearch globalSearch, Pageable pageable) {
        QFile file = QFile.file;
        JPQLQuery<File> query = from(file);
        Assert.notNull(box, "box is required");
        query.where(file.box.eq(box));
        Path<?>[] paths = new Path<?>[] { file.barcode, file.location, file.inventoryItem, file.box, file.createdDate, file.createdBy, file.modifiedDate, file.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, file.barcode).map(LOCATION, file.location).map(INVENTORY_ITEM, file.inventoryItem).map(BOX, file.box).map(CREATED_DATE, file.createdDate).map(CREATED_BY, file.createdBy).map(MODIFIED_DATE, file.modifiedDate).map(MODIFIED_BY, file.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, file);
    }
}
