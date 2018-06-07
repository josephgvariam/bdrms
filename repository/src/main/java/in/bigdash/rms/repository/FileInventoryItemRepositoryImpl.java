package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.inventory.QFileInventoryItem;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * = FileInventoryItemRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = FileInventoryItemRepositoryCustom.class)
@Transactional(readOnly = true)
public class FileInventoryItemRepositoryImpl extends QueryDslRepositorySupportExt<FileInventoryItem> implements FileInventoryItemRepositoryCustom {

    /**
     * Default constructor
     */
    FileInventoryItemRepositoryImpl() {
        super(FileInventoryItem.class);
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
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String FILE = "file";

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<FileInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QFileInventoryItem fileInventoryItem = QFileInventoryItem.fileInventoryItem;
        JPQLQuery<FileInventoryItem> query = from(fileInventoryItem);
        Path<?>[] paths = new Path<?>[] { fileInventoryItem.ref1, fileInventoryItem.ref2, fileInventoryItem.ref3, fileInventoryItem.ref4, fileInventoryItem.ref5, fileInventoryItem.status, fileInventoryItem.file };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, fileInventoryItem.ref1).map(REF_2, fileInventoryItem.ref2).map(REF_3, fileInventoryItem.ref3).map(REF_4, fileInventoryItem.ref4).map(REF_5, fileInventoryItem.ref5).map(STATUS, fileInventoryItem.status).map(FILE, fileInventoryItem.file);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, fileInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<FileInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QFileInventoryItem fileInventoryItem = QFileInventoryItem.fileInventoryItem;
        JPQLQuery<FileInventoryItem> query = from(fileInventoryItem);
        Path<?>[] paths = new Path<?>[] { fileInventoryItem.ref1, fileInventoryItem.ref2, fileInventoryItem.ref3, fileInventoryItem.ref4, fileInventoryItem.ref5, fileInventoryItem.status, fileInventoryItem.file };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(fileInventoryItem.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, fileInventoryItem.ref1).map(REF_2, fileInventoryItem.ref2).map(REF_3, fileInventoryItem.ref3).map(REF_4, fileInventoryItem.ref4).map(REF_5, fileInventoryItem.ref5).map(STATUS, fileInventoryItem.status).map(FILE, fileInventoryItem.file);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, fileInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<FileInventoryItem> findByFile(File file, GlobalSearch globalSearch, Pageable pageable) {
        QFileInventoryItem fileInventoryItem = QFileInventoryItem.fileInventoryItem;
        JPQLQuery<FileInventoryItem> query = from(fileInventoryItem);
        Assert.notNull(file, "file is required");
        query.where(fileInventoryItem.file.eq(file));
        Path<?>[] paths = new Path<?>[] { fileInventoryItem.ref1, fileInventoryItem.ref2, fileInventoryItem.ref3, fileInventoryItem.ref4, fileInventoryItem.ref5, fileInventoryItem.status, fileInventoryItem.file };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, fileInventoryItem.ref1).map(REF_2, fileInventoryItem.ref2).map(REF_3, fileInventoryItem.ref3).map(REF_4, fileInventoryItem.ref4).map(REF_5, fileInventoryItem.ref5).map(STATUS, fileInventoryItem.status).map(FILE, fileInventoryItem.file);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, fileInventoryItem);
    }
}
