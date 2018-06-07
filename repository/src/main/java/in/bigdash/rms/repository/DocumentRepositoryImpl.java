package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.Document;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.QDocument;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * = DocumentRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = DocumentRepositoryCustom.class)
@Transactional(readOnly = true)
public class DocumentRepositoryImpl extends QueryDslRepositorySupportExt<Document> implements DocumentRepositoryCustom {

    /**
     * Default constructor
     */
    DocumentRepositoryImpl() {
        super(Document.class);
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
    public static final String FILE = "file";

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
    public Page<Document> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QDocument document = QDocument.document;
        JPQLQuery<Document> query = from(document);
        Path<?>[] paths = new Path<?>[] { document.barcode, document.location, document.inventoryItem, document.file, document.createdDate, document.createdBy, document.modifiedDate, document.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, document.barcode).map(LOCATION, document.location).map(INVENTORY_ITEM, document.inventoryItem).map(FILE, document.file).map(CREATED_DATE, document.createdDate).map(CREATED_BY, document.createdBy).map(MODIFIED_DATE, document.modifiedDate).map(MODIFIED_BY, document.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, document);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Document> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QDocument document = QDocument.document;
        JPQLQuery<Document> query = from(document);
        Path<?>[] paths = new Path<?>[] { document.barcode, document.location, document.inventoryItem, document.file, document.createdDate, document.createdBy, document.modifiedDate, document.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(document.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, document.barcode).map(LOCATION, document.location).map(INVENTORY_ITEM, document.inventoryItem).map(FILE, document.file).map(CREATED_DATE, document.createdDate).map(CREATED_BY, document.createdBy).map(MODIFIED_DATE, document.modifiedDate).map(MODIFIED_BY, document.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, document);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param file
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<Document> findByFile(File file, GlobalSearch globalSearch, Pageable pageable) {
        QDocument document = QDocument.document;
        JPQLQuery<Document> query = from(document);
        Assert.notNull(file, "file is required");
        query.where(document.file.eq(file));
        Path<?>[] paths = new Path<?>[] { document.barcode, document.location, document.inventoryItem, document.file, document.createdDate, document.createdBy, document.modifiedDate, document.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(BARCODE, document.barcode).map(LOCATION, document.location).map(INVENTORY_ITEM, document.inventoryItem).map(FILE, document.file).map(CREATED_DATE, document.createdDate).map(CREATED_BY, document.createdBy).map(MODIFIED_DATE, document.modifiedDate).map(MODIFIED_BY, document.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, document);
    }
}
