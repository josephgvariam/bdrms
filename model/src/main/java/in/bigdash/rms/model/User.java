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
import javax.persistence.ManyToMany;

import in.bigdash.rms.model.request.Request;
import javax.persistence.OneToMany;
import io.springlets.format.EntityFormat;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Calendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_USER")
@EntityFormat("#{username} - #{name}")
@Audited
public class User {


    @Id
    @SequenceGenerator(name = "userGen", sequenceName = "BD_USER_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGen")
    @Column(name = "ID")
    private Long id;


    @Version
    @Column(name = "VERSION")
    private Long version;


    @NotNull
    @Column(name = "USERNAME", unique = true)
    private String username;


    @NotNull
    @Column(name = "PASSWORD")
    private String password;


    @NotNull
    @Column(name = "NAME")
    private String name;


    @NotNull
    @Column(name = "PHONE")
    private String phone;


    @NotNull
    @Column(name = "EMAIL")
    private String email;


    @Column(name = "EMPLOYEE_NUMBER")
    private String employeeNumber;


    @NotNull
    @Column(name = "LOCKED")
    private Boolean locked;

    @NotAudited
    @ManyToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Role> roles = new HashSet<Role>();

    @NotAudited
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "userCreated")
    private Set<Request> requestsCreated = new HashSet<Request>();


    @NotAudited
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "userAssigned")
    private Set<Request> requestsAssigned = new HashSet<Request>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    @EntityFormat
    private Client client;


    @Column(name = "CREATED_DATE")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar createdDate;


    @Column(name = "CREATED_BY")
    @CreatedBy
    private String createdBy;


    @Column(name = "MODIFIED_DATE")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Calendar modifiedDate;


    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private String modifiedBy;


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public Long getId() {
        return this.id;
    }


    public User setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public User setVersion(Long version) {
        this.version = version;
        return this;
    }


    public String getUsername() {
        return this.username;
    }


    public User setUsername(String username) {
        this.username = username;
        return this;
    }


    public String getPassword() {
        return this.password;
    }


    public User setPassword(String password) {
        this.password = password;
        return this;
    }


    public String getName() {
        return this.name;
    }


    public User setName(String name) {
        this.name = name;
        return this;
    }


    public String getPhone() {
        return this.phone;
    }


    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }


    public String getEmail() {
        return this.email;
    }


    public User setEmail(String email) {
        this.email = email;
        return this;
    }


    public String getEmployeeNumber() {
        return this.employeeNumber;
    }


    public User setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        return this;
    }


    public Boolean getLocked() {
        return this.locked;
    }


    public User setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }


    public Set<Role> getRoles() {
        return this.roles;
    }


    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }


    public Set<Request> getRequestsCreated() {
        return this.requestsCreated;
    }


    public User setRequestsCreated(Set<Request> requestsCreated) {
        this.requestsCreated = requestsCreated;
        return this;
    }


    public Set<Request> getRequestsAssigned() {
        return this.requestsAssigned;
    }


    public User setRequestsAssigned(Set<Request> requestsAssigned) {
        this.requestsAssigned = requestsAssigned;
        return this;
    }


    public Client getClient() {
        return this.client;
    }


    public User setClient(Client client) {
        this.client = client;
        return this;
    }


    public Calendar getCreatedDate() {
        return this.createdDate;
    }


    public User setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
        return this;
    }


    public String getCreatedBy() {
        return this.createdBy;
    }


    public User setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }


    public Calendar getModifiedDate() {
        return this.modifiedDate;
    }


    public User setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }


    public String getModifiedBy() {
        return this.modifiedBy;
    }


    public User setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // instanceof is false if the instance is null
        if (!(obj instanceof User)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((User) obj).getId());
    }


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "User {" + "id='" + id + '\'' + ", username='" + username + '\'' + "}";
    }


    public void addToRoles(Iterable<Role> rolesToAdd) {
        Assert.notNull(rolesToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Role item : rolesToAdd) {
            this.roles.add(item);
            item.getUsers().add(this);
        }
    }


    public void removeFromRoles(Iterable<Role> rolesToRemove) {
        Assert.notNull(rolesToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Role item : rolesToRemove) {
            this.roles.remove(item);
            item.getUsers().remove(this);
        }
    }


    public void addToRequestsCreated(Iterable<Request> requestsCreatedToAdd) {
        Assert.notNull(requestsCreatedToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsCreatedToAdd) {
            this.requestsCreated.add(item);
            item.setUserCreated(this);
        }
    }


    public void removeFromRequestsCreated(Iterable<Request> requestsCreatedToRemove) {
        Assert.notNull(requestsCreatedToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsCreatedToRemove) {
            this.requestsCreated.remove(item);
            item.setUserCreated(null);
        }
    }


    public void addToRequestsAssigned(Iterable<Request> requestsAssignedToAdd) {
        Assert.notNull(requestsAssignedToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsAssignedToAdd) {
            this.requestsAssigned.add(item);
            item.setUserAssigned(this);
        }
    }


    public void removeFromRequestsAssigned(Iterable<Request> requestsAssignedToRemove) {
        Assert.notNull(requestsAssignedToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsAssignedToRemove) {
            this.requestsAssigned.remove(item);
            item.setUserAssigned(null);
        }
    }
}
