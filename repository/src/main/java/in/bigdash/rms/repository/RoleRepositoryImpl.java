package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.Role;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.QRole;
import in.bigdash.rms.model.User;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Transactional(readOnly = true)
public class RoleRepositoryImpl extends QueryDslRepositorySupportExt<Role> implements RoleRepositoryCustom {


    RoleRepositoryImpl() {
        super(Role.class);
    }


    public static final String DESCRIPTION = "description";


    public static final String NAME = "name";


    public Page<Role> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QRole role = QRole.role;
        JPQLQuery<Role> query = from(role);
        Path<?>[] paths = new Path<?>[] { role.name, role.description };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(NAME, role.name).map(DESCRIPTION, role.description);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, role);
    }


    public Page<Role> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QRole role = QRole.role;
        JPQLQuery<Role> query = from(role);
        Path<?>[] paths = new Path<?>[] { role.name, role.description };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(role.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(NAME, role.name).map(DESCRIPTION, role.description);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, role);
    }


    public Page<Role> findByUsersContains(User users, GlobalSearch globalSearch, Pageable pageable) {
        QRole role = QRole.role;
        JPQLQuery<Role> query = from(role);
        Assert.notNull(users, "users is required");
        query.where(role.users.contains(users));
        Path<?>[] paths = new Path<?>[] { role.name, role.description };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(NAME, role.name).map(DESCRIPTION, role.description);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, role);
    }
}
