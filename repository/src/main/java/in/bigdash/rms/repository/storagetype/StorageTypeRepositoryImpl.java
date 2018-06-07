package in.bigdash.rms.repository.storagetype;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.StorageType;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.QStorageType;
import io.springlets.data.domain.GlobalSearch;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Transactional(readOnly = true)
public class StorageTypeRepositoryImpl extends QueryDslRepositorySupportExt<StorageType> implements StorageTypeRepositoryCustom {


    StorageTypeRepositoryImpl() {
        super(StorageType.class);
    }


    public static final String DESCRIPTION = "description";


    public static final String NAME = "name";


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
