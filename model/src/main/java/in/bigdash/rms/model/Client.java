package in.bigdash.rms.model;
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

import javax.persistence.ManyToMany;
import java.util.Calendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import io.springlets.format.EntityFormat;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_CLIENT")
@EntityFormat("#{name}")
public class Client {


    @Id
    @SequenceGenerator(name = "clientGen", sequenceName = "BD_CLIENT_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientGen")
    @Column(name = "ID")
    private Long id;


    @Version
    @Column(name = "VERSION")
    private Long version;


    @NotNull
    @Column(name = "NAME")
    private String name;


    @NotNull
    @Column(name = "department")
    private String department;


    @NotNull
    @Column(name = "ADDRESS")
    private String address;


    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "client")
    private Set<User> users = new HashSet<User>();


    @ManyToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "clients")
    private Set<StorageType> storageTypes = new HashSet<StorageType>();


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


    public Client setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public Client setVersion(Long version) {
        this.version = version;
        return this;
    }


    public String getName() {
        return this.name;
    }


    public Client setName(String name) {
        this.name = name;
        return this;
    }


    public String getDepartment() {
        return this.department;
    }


    public Client setDepartment(String department) {
        this.department = department;
        return this;
    }


    public String getAddress() {
        return this.address;
    }


    public Client setAddress(String address) {
        this.address = address;
        return this;
    }


    public Set<User> getUsers() {
        return this.users;
    }


    public Client setUsers(Set<User> users) {
        this.users = users;
        return this;
    }


    public Set<StorageType> getStorageTypes() {
        return this.storageTypes;
    }


    public Client setStorageTypes(Set<StorageType> storageTypes) {
        this.storageTypes = storageTypes;
        return this;
    }


    public Calendar getCreatedDate() {
        return this.createdDate;
    }


    public Client setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
        return this;
    }


    public String getCreatedBy() {
        return this.createdBy;
    }


    public Client setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }


    public Calendar getModifiedDate() {
        return this.modifiedDate;
    }


    public Client setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }


    public String getModifiedBy() {
        return this.modifiedBy;
    }


    public Client setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }


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


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "Client {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", name='" + name + '\'' + ", department='" + department + '\'' + ", address='" + address + '\'' + ", createdDate='" + createdDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(createdDate.getTime()) + '\'' + ", createdBy='" + createdBy + '\'' + ", modifiedDate='" + modifiedDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(modifiedDate.getTime()) + '\'' + ", modifiedBy='" + modifiedBy + '\'' + "}" + super.toString();
    }


    public void addToUsers(Iterable<User> usersToAdd) {
        Assert.notNull(usersToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (User item : usersToAdd) {
            this.users.add(item);
            item.setClient(this);
        }
    }


    public void removeFromUsers(Iterable<User> usersToRemove) {
        Assert.notNull(usersToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (User item : usersToRemove) {
            this.users.remove(item);
            item.setClient(null);
        }
    }


    public void addToStorageTypes(Iterable<StorageType> storageTypesToAdd) {
        Assert.notNull(storageTypesToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (StorageType item : storageTypesToAdd) {
            this.storageTypes.add(item);
            item.getClients().add(this);
        }
    }


    public void removeFromStorageTypes(Iterable<StorageType> storageTypesToRemove) {
        Assert.notNull(storageTypesToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (StorageType item : storageTypesToRemove) {
            this.storageTypes.remove(item);
            item.getClients().remove(this);
        }
    }
}
