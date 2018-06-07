package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
import in.bigdash.rms.model.User;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.QUser;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@RooJpaRepositoryCustomImpl(repository = UserRepositoryCustom.class)
@Transactional(readOnly = true)
public class UserRepositoryImpl extends QueryDslRepositorySupportExt<User> implements UserRepositoryCustom {


    UserRepositoryImpl() {
        super(User.class);
    }


    public static final String CREATED_BY = "createdBy";


    public static final String LOCKED = "locked";


    public static final String PASSWORD = "password";


    public static final String MODIFIED_BY = "modifiedBy";


    public static final String CREATED_DATE = "createdDate";


    public static final String PHONE = "phone";


    public static final String EMPLOYEE_NUMBER = "employeeNumber";


    public static final String USERNAME = "username";


    public static final String EMAIL = "email";


    public static final String CLIENT = "client";


    public static final String MODIFIED_DATE = "modifiedDate";


    public static final String NAME = "name";


    public Page<User> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QUser user = QUser.user;
        JPQLQuery<User> query = from(user);
        Path<?>[] paths = new Path<?>[] { user.username, user.password, user.name, user.phone, user.email, user.employeeNumber, user.locked, user.client, user.createdDate, user.createdBy, user.modifiedDate, user.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USERNAME, user.username).map(PASSWORD, user.password).map(NAME, user.name).map(PHONE, user.phone).map(EMAIL, user.email).map(EMPLOYEE_NUMBER, user.employeeNumber).map(LOCKED, user.locked).map(CLIENT, user.client).map(CREATED_DATE, user.createdDate).map(CREATED_BY, user.createdBy).map(MODIFIED_DATE, user.modifiedDate).map(MODIFIED_BY, user.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, user);
    }


    public Page<User> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QUser user = QUser.user;
        JPQLQuery<User> query = from(user);
        Path<?>[] paths = new Path<?>[] { user.username, user.password, user.name, user.phone, user.email, user.employeeNumber, user.locked, user.client, user.createdDate, user.createdBy, user.modifiedDate, user.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(user.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(USERNAME, user.username).map(PASSWORD, user.password).map(NAME, user.name).map(PHONE, user.phone).map(EMAIL, user.email).map(EMPLOYEE_NUMBER, user.employeeNumber).map(LOCKED, user.locked).map(CLIENT, user.client).map(CREATED_DATE, user.createdDate).map(CREATED_BY, user.createdBy).map(MODIFIED_DATE, user.modifiedDate).map(MODIFIED_BY, user.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, user);
    }


    public Page<User> findByClient(Client client, GlobalSearch globalSearch, Pageable pageable) {
        QUser user = QUser.user;
        JPQLQuery<User> query = from(user);
        Assert.notNull(client, "client is required");
        query.where(user.client.eq(client));
        Path<?>[] paths = new Path<?>[] { user.username, user.password, user.name, user.phone, user.email, user.employeeNumber, user.locked, user.client, user.createdDate, user.createdBy, user.modifiedDate, user.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(USERNAME, user.username).map(PASSWORD, user.password).map(NAME, user.name).map(PHONE, user.phone).map(EMAIL, user.email).map(EMPLOYEE_NUMBER, user.employeeNumber).map(LOCKED, user.locked).map(CLIENT, user.client).map(CREATED_DATE, user.createdDate).map(CREATED_BY, user.createdBy).map(MODIFIED_DATE, user.modifiedDate).map(MODIFIED_BY, user.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, user);
    }
}
