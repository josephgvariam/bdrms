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


@RooJpaRepositoryCustomImpl(repository = DocumentRepositoryCustom.class)
@Transactional(readOnly = true)
public class DocumentRepositoryImpl extends QueryDslRepositorySupportExt<Document> implements DocumentRepositoryCustom {


    DocumentRepositoryImpl() {
        super(Document.class);
    }


    public static final String LOCATION = "location";


    public static final String CREATED_BY = "createdBy";


    public static final String MODIFIED_BY = "modifiedBy";


    public static final String CREATED_DATE = "createdDate";


    public static final String BARCODE = "barcode";


    public static final String FILE = "file";


    public static final String MODIFIED_DATE = "modifiedDate";


    public static final String INVENTORY_ITEM = "inventoryItem";


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
