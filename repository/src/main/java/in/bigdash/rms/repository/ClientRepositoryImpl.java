package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import in.bigdash.rms.model.Client;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.JPQLQuery;
import in.bigdash.rms.model.QClient;
import io.springlets.data.domain.GlobalSearch;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt.AttributeMappingBuilder;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public class ClientRepositoryImpl extends QueryDslRepositorySupportExt<Client> implements ClientRepositoryCustom {


    ClientRepositoryImpl() {
        super(Client.class);
    }


    public static final String CREATED_BY = "createdBy";


    public static final String MODIFIED_BY = "modifiedBy";


    public static final String CREATED_DATE = "createdDate";


    public static final String ADDRESS = "address";


    public static final String DEPARTMENT = "department";


    public static final String MODIFIED_DATE = "modifiedDate";


    public static final String NAME = "name";


    public Page<Client> findAll(GlobalSearch globalSearch, Pageable pageable) {
        QClient client = QClient.client;
        JPQLQuery<Client> query = from(client);
        Path<?>[] paths = new Path<?>[] { client.name, client.department, client.address, client.createdDate, client.createdBy, client.modifiedDate, client.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        AttributeMappingBuilder mapping = buildMapper().map(NAME, client.name).map(DEPARTMENT, client.department).map(ADDRESS, client.address).map(CREATED_DATE, client.createdDate).map(CREATED_BY, client.createdBy).map(MODIFIED_DATE, client.modifiedDate).map(MODIFIED_BY, client.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, client);
    }


    public Page<Client> findAllByIdsIn(List<Long> ids, GlobalSearch globalSearch, Pageable pageable) {
        QClient client = QClient.client;
        JPQLQuery<Client> query = from(client);
        Path<?>[] paths = new Path<?>[] { client.name, client.department, client.address, client.createdDate, client.createdBy, client.modifiedDate, client.modifiedBy };
        applyGlobalSearch(globalSearch, query, paths);
        // Also, filter by the provided ids
        query.where(client.id.in(ids));
        AttributeMappingBuilder mapping = buildMapper().map(NAME, client.name).map(DEPARTMENT, client.department).map(ADDRESS, client.address).map(CREATED_DATE, client.createdDate).map(CREATED_BY, client.createdBy).map(MODIFIED_DATE, client.modifiedDate).map(MODIFIED_BY, client.modifiedBy);
        applyPagination(pageable, query, mapping);
        applyOrderById(query);
        return loadPage(query, pageable, client);
    }
}
