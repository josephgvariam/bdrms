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
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.roo.addon.jpa.annotations.entity.JpaRelationType;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaRelation;
import javax.persistence.ManyToMany;
import java.util.Calendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.jpa.annotations.audit.RooJpaAudit;
import io.springlets.format.EntityFormat;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

/**
 * = Client
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity(table = "BD_CLIENT", entityFormatExpression = "#{name}")
@RooEquals(isJpaEntity = true)
@RooJpaAudit
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_CLIENT")
@EntityFormat("#{name}")
public class Client {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Id
    @SequenceGenerator(name = "clientGen", sequenceName = "BD_CLIENT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientGen")
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
    @NotNull
    @Column(name = "NAME")
    private String name;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @NotNull
    @Column(name = "department")
    private String department;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @NotNull
    @Column(name = "ADDRESS")
    private String address;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "client")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<User> users = new HashSet<User>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "clients")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<StorageType> storageTypes = new HashSet<StorageType>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "CREATED_DATE")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar createdDate;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "CREATED_BY")
    @CreatedBy
    private String createdBy;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "MODIFIED_DATE")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar modifiedDate;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private String modifiedBy;

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
     * @return Client
     */
    public Client setId(Long id) {
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
     * @return Client
     */
    public Client setVersion(Long version) {
        this.version = version;
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
     * @return Client
     */
    public Client setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets department value
     *
     * @return String
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * Sets department value
     *
     * @param department
     * @return Client
     */
    public Client setDepartment(String department) {
        this.department = department;
        return this;
    }

    /**
     * Gets address value
     *
     * @return String
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets address value
     *
     * @param address
     * @return Client
     */
    public Client setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Gets users value
     *
     * @return Set
     */
    public Set<User> getUsers() {
        return this.users;
    }

    /**
     * Sets users value
     *
     * @param users
     * @return Client
     */
    public Client setUsers(Set<User> users) {
        this.users = users;
        return this;
    }

    /**
     * Gets storageTypes value
     *
     * @return Set
     */
    public Set<StorageType> getStorageTypes() {
        return this.storageTypes;
    }

    /**
     * Sets storageTypes value
     *
     * @param storageTypes
     * @return Client
     */
    public Client setStorageTypes(Set<StorageType> storageTypes) {
        this.storageTypes = storageTypes;
        return this;
    }

    /**
     * Gets createdDate value
     *
     * @return Calendar
     */
    public Calendar getCreatedDate() {
        return this.createdDate;
    }

    /**
     * Sets createdDate value
     *
     * @param createdDate
     * @return Client
     */
    public Client setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * Gets createdBy value
     *
     * @return String
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Sets createdBy value
     *
     * @param createdBy
     * @return Client
     */
    public Client setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Gets modifiedDate value
     *
     * @return Calendar
     */
    public Calendar getModifiedDate() {
        return this.modifiedDate;
    }

    /**
     * Sets modifiedDate value
     *
     * @param modifiedDate
     * @return Client
     */
    public Client setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    /**
     * Gets modifiedBy value
     *
     * @return String
     */
    public String getModifiedBy() {
        return this.modifiedBy;
    }

    /**
     * Sets modifiedBy value
     *
     * @param modifiedBy
     * @return Client
     */
    public Client setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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
        if (!(obj instanceof Client)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((Client) obj).getId());
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
        return "Client {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", name='" + name + '\'' + ", department='" + department + '\'' + ", address='" + address + '\'' + ", createdDate='" + createdDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(createdDate.getTime()) + '\'' + ", createdBy='" + createdBy + '\'' + ", modifiedDate='" + modifiedDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(modifiedDate.getTime()) + '\'' + ", modifiedBy='" + modifiedBy + '\'' + "}" + super.toString();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param usersToAdd
     */
    public void addToUsers(Iterable<User> usersToAdd) {
        Assert.notNull(usersToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (User item : usersToAdd) {
            this.users.add(item);
            item.setClient(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param usersToRemove
     */
    public void removeFromUsers(Iterable<User> usersToRemove) {
        Assert.notNull(usersToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (User item : usersToRemove) {
            this.users.remove(item);
            item.setClient(null);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageTypesToAdd
     */
    public void addToStorageTypes(Iterable<StorageType> storageTypesToAdd) {
        Assert.notNull(storageTypesToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (StorageType item : storageTypesToAdd) {
            this.storageTypes.add(item);
            item.getClients().add(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param storageTypesToRemove
     */
    public void removeFromStorageTypes(Iterable<StorageType> storageTypesToRemove) {
        Assert.notNull(storageTypesToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (StorageType item : storageTypesToRemove) {
            this.storageTypes.remove(item);
            item.getClients().remove(this);
        }
    }
}
