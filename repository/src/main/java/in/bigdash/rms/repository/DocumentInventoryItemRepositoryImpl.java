package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.inventory.DocumentInventoryItem;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.Document;
import in.bigdash.rms.model.inventory.QDocumentInventoryItem;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * = DocumentInventoryItemRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = DocumentInventoryItemRepositoryCustom.class)
@Transactional(readOnly = true)
public class DocumentInventoryItemRepositoryImpl extends QueryDslRepositorySupportExt<DocumentInventoryItem> implements DocumentInventoryItemRepositoryCustom {

    /**
     * Default constructor
     */
    DocumentInventoryItemRepositoryImpl() {
        super(DocumentInventoryItem.class);
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
    public static final String DOCUMENT = "document";

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
    public Page<DocumentInventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QDocumentInventoryItem documentInventoryItem = QDocumentInventoryItem.documentInventoryItem;
        JPQLQuery<DocumentInventoryItem> query = from(documentInventoryItem);
        Path<?>[] paths = new Path<?>[] { documentInventoryItem.ref1, documentInventoryItem.ref2, documentInventoryItem.ref3, documentInventoryItem.ref4, documentInventoryItem.ref5, documentInventoryItem.status, documentInventoryItem.document };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, documentInventoryItem.ref1).map(REF_2, documentInventoryItem.ref2).map(REF_3, documentInventoryItem.ref3).map(REF_4, documentInventoryItem.ref4).map(REF_5, documentInventoryItem.ref5).map(STATUS, documentInventoryItem.status).map(DOCUMENT, documentInventoryItem.document);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, documentInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<DocumentInventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QDocumentInventoryItem documentInventoryItem = QDocumentInventoryItem.documentInventoryItem;
        JPQLQuery<DocumentInventoryItem> query = from(documentInventoryItem);
        Path<?>[] paths = new Path<?>[] { documentInventoryItem.ref1, documentInventoryItem.ref2, documentInventoryItem.ref3, documentInventoryItem.ref4, documentInventoryItem.ref5, documentInventoryItem.status, documentInventoryItem.document };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(documentInventoryItem.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, documentInventoryItem.ref1).map(REF_2, documentInventoryItem.ref2).map(REF_3, documentInventoryItem.ref3).map(REF_4, documentInventoryItem.ref4).map(REF_5, documentInventoryItem.ref5).map(STATUS, documentInventoryItem.status).map(DOCUMENT, documentInventoryItem.document);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, documentInventoryItem);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param document
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<DocumentInventoryItem> findByDocument(Document document, GlobalSearch globalSearch, Pageable pageable) {
        QDocumentInventoryItem documentInventoryItem = QDocumentInventoryItem.documentInventoryItem;
        JPQLQuery<DocumentInventoryItem> query = from(documentInventoryItem);
        Assert.notNull(document, "document is required");
        query.where(documentInventoryItem.document.eq(document));
        Path<?>[] paths = new Path<?>[] { documentInventoryItem.ref1, documentInventoryItem.ref2, documentInventoryItem.ref3, documentInventoryItem.ref4, documentInventoryItem.ref5, documentInventoryItem.status, documentInventoryItem.document };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, documentInventoryItem.ref1).map(REF_2, documentInventoryItem.ref2).map(REF_3, documentInventoryItem.ref3).map(REF_4, documentInventoryItem.ref4).map(REF_5, documentInventoryItem.ref5).map(STATUS, documentInventoryItem.status).map(DOCUMENT, documentInventoryItem.document);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, documentInventoryItem);
    }
}
