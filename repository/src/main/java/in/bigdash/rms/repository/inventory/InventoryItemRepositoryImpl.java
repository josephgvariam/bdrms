package in.bigdash.rms.repository.inventory;
import com.querydsl.core.BooleanBuilder;
import in.bigdash.rms.model.*;
import in.bigdash.rms.model.inventory.*;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
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
import org.springframework.util.StringUtils;


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

        if (globalSearch != null) {
            String text = globalSearch.getText();
            if (text != null && !StringUtils.isEmpty(text.trim())) {
                text = text.toLowerCase().trim();

                QBox box = QBox.box;
                QFile file = QFile.file;
                QDocument doc = QDocument.document;
                QBox fileBox = new QBox("fileBox");
                QFile docFile = new QFile("docFile");
                QBox docFileBox = new QBox("docFileBox");

                query.leftJoin(inventoryItem.as(QBoxInventoryItem.class).box, box);
                query.leftJoin(inventoryItem.as(QFileInventoryItem.class).file, file);
                query.leftJoin(inventoryItem.as(QDocumentInventoryItem.class).document, doc);
                query.leftJoin(inventoryItem.as(QFileInventoryItem.class).file.box, fileBox);
                query.leftJoin(inventoryItem.as(QDocumentInventoryItem.class).document.file, docFile);
                query.leftJoin(inventoryItem.as(QDocumentInventoryItem.class).document.file.box, docFileBox);

                BooleanBuilder searchCondition = new BooleanBuilder()
                        .or(box.barcode.containsIgnoreCase(text))
                        .or(file.barcode.containsIgnoreCase(text))
                        .or(doc.barcode.containsIgnoreCase(text))
                        .or(fileBox.barcode.containsIgnoreCase(text))
                        .or(docFile.barcode.containsIgnoreCase(text))
                        .or(docFileBox.barcode.containsIgnoreCase(text))
                        .or(inventoryItem.ref1.containsIgnoreCase(text))
                        .or(inventoryItem.ref2.containsIgnoreCase(text))
                        .or(inventoryItem.ref3.containsIgnoreCase(text))
                        .or(inventoryItem.ref4.containsIgnoreCase(text))
                        .or(inventoryItem.ref5.containsIgnoreCase(text))
                        ;

                query.where(searchCondition);
            }
        }

        AttributeMappingBuilder mapping = buildMapper().map("id", inventoryItem.id);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, inventoryItem);
    }


    public List<InventoryItem> findByRequestTypeAndStorageType(String requestType, String storageType){
        //TODO different query for different request types?
        QInventoryItem inventoryItem = QInventoryItem.inventoryItem;
        JPQLQuery<InventoryItem> query = from(inventoryItem);

        User currentUser = getCurrentUser();

        query.where(inventoryItem.userCreated.client.eq(currentUser.getClient()));
        query.where(inventoryItem.type.eq(storageType));
        query.where(inventoryItem.status.eq(InventoryItemStatus.STORED));

        applyOrderById(query);

        return query.fetch();
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
