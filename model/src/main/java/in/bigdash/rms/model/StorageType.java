package in.bigdash.rms.model;
import org.springframework.roo.addon.javabean.annotations.RooEquals;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import in.bigdash.rms.model.request.Request;
import javax.persistence.OneToMany;
import org.springframework.roo.addon.jpa.annotations.entity.JpaRelationType;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaRelation;
import io.springlets.format.EntityFormat;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.util.Assert;

/**
 * = StorageType
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity(table = "BD_STORAGE_TYPE", entityFormatExpression = "#{name}")
@RooEquals(isJpaEntity = true)
@Entity
@Table(name = "BD_STORAGE_TYPE")
@EntityFormat("#{name}")
public class StorageType {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Id
    @SequenceGenerator(name = "storageTypeGen", sequenceName = "BD_STORAGE_TYPE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storageTypeGen")
    @Column(name = "ID")
    private Long id;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Version
    @Column(name = "VERSION")
    private Long version;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bd_client_storage_types", joinColumns = { @JoinColumn(name = "client_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "storage_type_id", referencedColumnName = "id") })
    private Set<Client> clients = new HashSet<Client>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @NotNull
    @Column(name = "NAME")
    private String name;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "storageType")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<Request> requests = new HashSet<Request>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";

    /**
     * Gets id value
     *
     * @return Long
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets id value
     *
     * @param id
     * @return StorageType
     */
    public StorageType setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Gets version value
     *
     * @return Long
     */
    public Long getVersion() {
        return this.version;
    }

    /**
     * Sets version value
     *
     * @param version
     * @return StorageType
     */
    public StorageType setVersion(Long version) {
        this.version = version;
        return this;
    }

    /**
     * Gets clients value
     *
     * @return Set
     */
    public Set<Client> getClients() {
        return this.clients;
    }

    /**
     * Sets clients value
     *
     * @param clients
     * @return StorageType
     */
    public StorageType setClients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }

    /**
     * Gets name value
     *
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets name value
     *
     * @param name
     * @return StorageType
     */
    public StorageType setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets description value
     *
     * @return String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets description value
     *
     * @param description
     * @return StorageType
     */
    public StorageType setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Gets requests value
     *
     * @return Set
     */
    public Set<Request> getRequests() {
        return this.requests;
    }

    /**
     * Sets requests value
     *
     * @param requests
     * @return StorageType
     */
    public StorageType setRequests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }

    /**
     * This `equals` implementation is specific for JPA entities and uses
     * the entity identifier for it, following the article in
     * https://vladmihalcea.com/2016/06/06/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
     *
     * @param obj
     * @return Boolean
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // instanceof is false if the instance is null
        if (!(obj instanceof StorageType)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((StorageType) obj).getId());
    }

    /**
     * This `hashCode` implementation is specific for JPA entities and uses a fixed `int` value to be able
     * to identify the entity in collections after a new id is assigned to the entity, following the article in
     * https://vladmihalcea.com/2016/06/06/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
     *
     * @return Integer
     */
    public int hashCode() {
        return 31;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String toString() {
        return "StorageType {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + "}" + super.toString();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestsToAdd
     */
    public void addToRequests(Iterable<Request> requestsToAdd) {
        Assert.notNull(requestsToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsToAdd) {
            this.requests.add(item);
            item.setStorageType(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestsToRemove
     */
    public void removeFromRequests(Iterable<Request> requestsToRemove) {
        Assert.notNull(requestsToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsToRemove) {
            this.requests.remove(item);
            item.setStorageType(null);
        }
    }
}
