package in.bigdash.rms.repository;
import io.springlets.data.jpa.repository.support.QueryDslRepositorySupportExt;
import org.springframework.roo.addon.layers.repository.jpa.annotations.RooJpaRepositoryCustomImpl;
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

/**
 * = ClientRepositoryImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJpaRepositoryCustomImpl(repository = ClientRepositoryCustom.class)
@Transactional(readOnly = true)
public class ClientRepositoryImpl extends QueryDslRepositorySupportExt<Client> implements ClientRepositoryCustom {

    /**
     * Default constructor
     */
    ClientRepositoryImpl() {
        super(Client.class);
    }

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String CREATED_BY = "createdBy";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String MODIFIED_BY = "modifiedBy";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String CREATED_DATE = "createdDate";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String ADDRESS = "address";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String DEPARTMENT = "department";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String MODIFIED_DATE = "modifiedDate";

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

    /**
     * TODO Auto-generated method documentation
     *
     * @param ids
     * @param globalSearch
     * @param pageable
     * @return Page
     */
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
