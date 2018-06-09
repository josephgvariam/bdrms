package in.bigdash.rms.repository.inventory;
import in.bigdash.rms.model.QUser;
import in.bigdash.rms.model.Role;
import in.bigdash.rms.model.User;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.inventory.InventoryItem;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.inventory.QInventoryItem;
import in.bigdash.rms.model.request.Request;
import io.springlets.data.domain.GlobalSearch;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Transactional(readOnly = true)
public class InventoryItemRepositoryImpl extends QueryDslRepositorySupportExt<InventoryItem> implements InventoryItemRepositoryCustom {


    InventoryItemRepositoryImpl() {
        super(InventoryItem.class);
    }


    public static final String STATUS = "status";


    public static final String REF_3 = "ref3";


    public static final String REF_2 = "ref2";


    public static final String REF_1 = "ref1";


    public static final String REF_5 = "ref5";


    public static final String REF_4 = "ref4";


    public Page<InventoryItem> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QInventoryItem inventoryItem = QInventoryItem.inventoryItem;
        JPQLQuery<InventoryItem> query = from(inventoryItem);

        User currentUser = getCurrentUser();
        if(!userHasRole(currentUser, "ROLE_OPERATOR")) {
            query.where(inventoryItem.userCreated.client.eq(currentUser.getClient()));
        }

        Path<?>[] paths = new Path<?>[] { inventoryItem.ref1, inventoryItem.ref2, inventoryItem.ref3, inventoryItem.ref4, inventoryItem.ref5, inventoryItem.status };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, inventoryItem.ref1).map(REF_2, inventoryItem.ref2).map(REF_3, inventoryItem.ref3).map(REF_4, inventoryItem.ref4).map(REF_5, inventoryItem.ref5).map(STATUS, inventoryItem.status);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, inventoryItem);
    }


    public Page<InventoryItem> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QInventoryItem inventoryItem = QInventoryItem.inventoryItem;
        JPQLQuery<InventoryItem> query = from(inventoryItem);
        Path<?>[] paths = new Path<?>[] { inventoryItem.ref1, inventoryItem.ref2, inventoryItem.ref3, inventoryItem.ref4, inventoryItem.ref5, inventoryItem.status };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(inventoryItem.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, inventoryItem.ref1).map(REF_2, inventoryItem.ref2).map(REF_3, inventoryItem.ref3).map(REF_4, inventoryItem.ref4).map(REF_5, inventoryItem.ref5).map(STATUS, inventoryItem.status);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, inventoryItem);
    }


    public Page<InventoryItem> findByRequestsContains(Request requests, GlobalSearch globalSearch, Pageable pageable) {
        QInventoryItem inventoryItem = QInventoryItem.inventoryItem;
        JPQLQuery<InventoryItem> query = from(inventoryItem);
        Assert.notNull(requests, "requests is required");
        query.where(inventoryItem.requests.contains(requests));
        Path<?>[] paths = new Path<?>[] { inventoryItem.ref1, inventoryItem.ref2, inventoryItem.ref3, inventoryItem.ref4, inventoryItem.ref5, inventoryItem.status };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(REF_1, inventoryItem.ref1).map(REF_2, inventoryItem.ref2).map(REF_3, inventoryItem.ref3).map(REF_4, inventoryItem.ref4).map(REF_5, inventoryItem.ref5).map(STATUS, inventoryItem.status);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, inventoryItem);
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

    private boolean userHasRole(User user, String role)
    {
        if(user != null) {
            for (Role r : user.getRoles()) {
                if (r.getName().equals(role)) {
                    return true;
                }
            }
        }

        return false;
    }

}
