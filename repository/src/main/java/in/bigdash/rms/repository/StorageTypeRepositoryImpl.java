package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.StorageType;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.QStorageType;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * = StorageTypeRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = StorageTypeRepositoryCustom.class)
@Transactional(readOnly = true)
public class StorageTypeRepositoryImpl extends QueryDslRepositorySupportExt<StorageType> implements StorageTypeRepositoryCustom {

    /**
     * Default constructor
     */
    StorageTypeRepositoryImpl() {
        super(StorageType.class);
    }

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String DESCRIPTION = "description";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String NAME = "name";

    /**
     * TODO Auto-generated method documentation
     *
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<StorageType> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QStorageType storageType = QStorageType.storageType;
        JPQLQuery<StorageType> query = from(storageType);
        Path<?>[] paths = new Path<?>[] { storageType.name, storageType.description };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(NAME, storageType.name).map(DESCRIPTION, storageType.description);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, storageType);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<StorageType> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QStorageType storageType = QStorageType.storageType;
        JPQLQuery<StorageType> query = from(storageType);
        Path<?>[] paths = new Path<?>[] { storageType.name, storageType.description };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(storageType.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(NAME, storageType.name).map(DESCRIPTION, storageType.description);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, storageType);
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param clients
     * @param globalSearch
     * @param pageable
     * @return Page
     */
    public Page<StorageType> findByClientsContains(Client clients, GlobalSearch globalSearch, Pageable pageable) {
        QStorageType storageType = QStorageType.storageType;
        JPQLQuery<StorageType> query = from(storageType);
        Assert.notNull(clients, "clients is required");
        query.where(storageType.clients.contains(clients));
        Path<?>[] paths = new Path<?>[] { storageType.name, storageType.description };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(NAME, storageType.name).map(DESCRIPTION, storageType.description);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, storageType);
    }
}
