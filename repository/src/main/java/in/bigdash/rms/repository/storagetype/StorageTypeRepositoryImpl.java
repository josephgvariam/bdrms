package in.bigdash.rms.repository.storagetype;
import in.bigdash.rms.model.*;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import io.springlets.data.domain.GlobalSearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Page<StorageType> findAllByCurrentUser(GlobalSearch globalSearch, Pageable pageable) {
        User currentUser = getCurrentUser();
        List storageTypes = new ArrayList();

        if(currentUser != null) {
            for(StorageType st : currentUser.getClient().getStorageTypes())
            {
                storageTypes.add(st);
            }
        }

        Page<StorageType> page = new PageImpl<StorageType>(storageTypes, pageable, storageTypes.size());
        return page;
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

    private User getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            QUser user = QUser.user;
            return from(user).where(user.username.eq(currentUserName)).fetchOne();
        }
        return null;
    }
}
