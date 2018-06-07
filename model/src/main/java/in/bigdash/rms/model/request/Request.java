package in.bigdash.rms.model.request;
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

import java.util.Calendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Objects;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_REQUEST")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@EntityFormat("#{id}")
public class Request {


    @Id
    @SequenceGenerator(name = "requestGen", sequenceName = "BD_REQUEST_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requestGen")
    @Column(name = "ID")
    private Long id;


    @Version
    @Column(name = "VERSION")
    private Long version;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_CREATED_ID")
    @EntityFormat
    private User userCreated;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ASSIGNED_ID")
    @EntityFormat
    private User userAssigned;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORAGE_TYPE_ID")
    @EntityFormat
    private StorageType storageType;


    @NotNull
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;


    @Column(name = "NOTES")
    private String notes;


    @ManyToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "requests")
    private Set<InventoryItem> inventoryItems = new HashSet<InventoryItem>();


    @Column(name = "CREATED_DATE")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar createdDate;


    @Column(name = "CREATED_BY")
    @CreatedBy
    private String createdBy;


    @Column(name = "MODIFIED_DATE")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar modifiedDate;


    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private String modifiedBy;


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public Long getId() {
        return this.id;
    }


    public Request setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public Request setVersion(Long version) {
        this.version = version;
        return this;
    }


    public User getUserCreated() {
        return this.userCreated;
    }


    public Request setUserCreated(User userCreated) {
        this.userCreated = userCreated;
        return this;
    }


    public User getUserAssigned() {
        return this.userAssigned;
    }


    public Request setUserAssigned(User userAssigned) {
        this.userAssigned = userAssigned;
        return this;
    }


    public StorageType getStorageType() {
        return this.storageType;
    }


    public Request setStorageType(StorageType storageType) {
        this.storageType = storageType;
        return this;
    }


    public RequestStatus getStatus() {
        return this.status;
    }


    public Request setStatus(RequestStatus status) {
        this.status = status;
        return this;
    }


    public String getNotes() {
        return this.notes;
    }


    public Request setNotes(String notes) {
        this.notes = notes;
        return this;
    }


    public Set<InventoryItem> getInventoryItems() {
        return this.inventoryItems;
    }


    public Request setInventoryItems(Set<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
        return this;
    }


    public Calendar getCreatedDate() {
        return this.createdDate;
    }


    public Request setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
        return this;
    }


    public String getCreatedBy() {
        return this.createdBy;
    }


    public Request setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }


    public Calendar getModifiedDate() {
        return this.modifiedDate;
    }


    public Request setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }


    public String getModifiedBy() {
        return this.modifiedBy;
    }


    public Request setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }


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


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "Request {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", notes='" + notes + '\'' + ", createdDate='" + createdDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(createdDate.getTime()) + '\'' + ", createdBy='" + createdBy + '\'' + ", modifiedDate='" + modifiedDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(modifiedDate.getTime()) + '\'' + ", modifiedBy='" + modifiedBy + '\'' + "}" + super.toString();
    }


    public void addToInventoryItems(Iterable<InventoryItem> inventoryItemsToAdd) {
        Assert.notNull(inventoryItemsToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (InventoryItem item : inventoryItemsToAdd) {
            this.inventoryItems.add(item);
            item.getRequests().add(this);
        }
    }


    public void removeFromInventoryItems(Iterable<InventoryItem> inventoryItemsToRemove) {
        Assert.notNull(inventoryItemsToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (InventoryItem item : inventoryItemsToRemove) {
            this.inventoryItems.remove(item);
            item.getRequests().remove(this);
        }
    }
}
