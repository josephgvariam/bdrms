package in.bigdash.rms.model.request;
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
import in.bigdash.rms.model.User;
import io.springlets.format.EntityFormat;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import in.bigdash.rms.model.StorageType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import in.bigdash.rms.model.inventory.InventoryItem;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ManyToMany;
import org.springframework.roo.addon.jpa.annotations.entity.JpaRelationType;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaRelation;
import java.util.Calendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.jpa.annotations.audit.RooJpaAudit;
import java.util.Objects;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

/**
 * = Request
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity(inheritanceType = "SINGLE_TABLE", table = "BD_REQUEST", entityFormatExpression = "#{id}")
@RooEquals(isJpaEntity = true)
@RooJpaAudit
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_REQUEST")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@EntityFormat("#{id}")
public class Request {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Id
    @SequenceGenerator(name = "requestGen", sequenceName = "BD_REQUEST_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requestGen")
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_CREATED_ID")
    @EntityFormat
    private User userCreated;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ASSIGNED_ID")
    @EntityFormat
    private User userAssigned;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORAGE_TYPE_ID")
    @EntityFormat
    private StorageType storageType;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @NotNull
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "NOTES")
    private String notes;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "requests")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<InventoryItem> inventoryItems = new HashSet<InventoryItem>();

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
     * @return Request
     */
    public Request setId(Long id) {
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
     * @return Request
     */
    public Request setVersion(Long version) {
        this.version = version;
        return this;
    }

    /**
     * Gets userCreated value
     *
     * @return User
     */
    public User getUserCreated() {
        return this.userCreated;
    }

    /**
     * Sets userCreated value
     *
     * @param userCreated
     * @return Request
     */
    public Request setUserCreated(User userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    /**
     * Gets userAssigned value
     *
     * @return User
     */
    public User getUserAssigned() {
        return this.userAssigned;
    }

    /**
     * Sets userAssigned value
     *
     * @param userAssigned
     * @return Request
     */
    public Request setUserAssigned(User userAssigned) {
        this.userAssigned = userAssigned;
        return this;
    }

    /**
     * Gets storageType value
     *
     * @return StorageType
     */
    public StorageType getStorageType() {
        return this.storageType;
    }

    /**
     * Sets storageType value
     *
     * @param storageType
     * @return Request
     */
    public Request setStorageType(StorageType storageType) {
        this.storageType = storageType;
        return this;
    }

    /**
     * Gets status value
     *
     * @return RequestStatus
     */
    public RequestStatus getStatus() {
        return this.status;
    }

    /**
     * Sets status value
     *
     * @param status
     * @return Request
     */
    public Request setStatus(RequestStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Gets notes value
     *
     * @return String
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Sets notes value
     *
     * @param notes
     * @return Request
     */
    public Request setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Gets inventoryItems value
     *
     * @return Set
     */
    public Set<InventoryItem> getInventoryItems() {
        return this.inventoryItems;
    }

    /**
     * Sets inventoryItems value
     *
     * @param inventoryItems
     * @return Request
     */
    public Request setInventoryItems(Set<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
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
     * @return Request
     */
    public Request setCreatedDate(Calendar createdDate) {
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
     * @return Request
     */
    public Request setCreatedBy(String createdBy) {
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
     * @return Request
     */
    public Request setModifiedDate(Calendar modifiedDate) {
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
     * @return Request
     */
    public Request setModifiedBy(String modifiedBy) {
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
        if (!(obj instanceof Request)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((Request) obj).getId());
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
        return "Request {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", notes='" + notes + '\'' + ", createdDate='" + createdDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(createdDate.getTime()) + '\'' + ", createdBy='" + createdBy + '\'' + ", modifiedDate='" + modifiedDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(modifiedDate.getTime()) + '\'' + ", modifiedBy='" + modifiedBy + '\'' + "}" + super.toString();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItemsToAdd
     */
    public void addToInventoryItems(Iterable<InventoryItem> inventoryItemsToAdd) {
        Assert.notNull(inventoryItemsToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (InventoryItem item : inventoryItemsToAdd) {
            this.inventoryItems.add(item);
            item.getRequests().add(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItemsToRemove
     */
    public void removeFromInventoryItems(Iterable<InventoryItem> inventoryItemsToRemove) {
        Assert.notNull(inventoryItemsToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (InventoryItem item : inventoryItemsToRemove) {
            this.inventoryItems.remove(item);
            item.getRequests().remove(this);
        }
    }
}
