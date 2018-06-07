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
import javax.persistence.ManyToMany;
import org.springframework.roo.addon.jpa.annotations.entity.JpaRelationType;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaRelation;
import in.bigdash.rms.model.request.Request;
import javax.persistence.OneToMany;
import io.springlets.format.EntityFormat;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

/**
 * = User
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity(table = "BD_USER", entityFormatExpression = "#{username} - #{name}")
@RooEquals(isJpaEntity = true)
@RooJpaAudit
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_USER")
@EntityFormat("#{username} - #{name}")
public class User {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Id
    @SequenceGenerator(name = "userGen", sequenceName = "BD_USER_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGen")
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
    @Column(name = "USERNAME", unique = true)
    private String username;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @NotNull
    @Column(name = "PASSWORD")
    private String password;

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
    @Column(name = "PHONE")
    private String phone;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @NotNull
    @Column(name = "EMAIL")
    private String email;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "EMPLOYEE_NUMBER")
    private String employeeNumber;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @NotNull
    @Column(name = "LOCKED")
    private Boolean locked;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "users")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<Role> roles = new HashSet<Role>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "userCreated")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<Request> requestsCreated = new HashSet<Request>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "userAssigned")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<Request> requestsAssigned = new HashSet<Request>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    @EntityFormat
    private Client client;

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
     * @return User
     */
    public User setId(Long id) {
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
     * @return User
     */
    public User setVersion(Long version) {
        this.version = version;
        return this;
    }

    /**
     * Gets username value
     *
     * @return String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets username value
     *
     * @param username
     * @return User
     */
    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Gets password value
     *
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets password value
     *
     * @param password
     * @return User
     */
    public User setPassword(String password) {
        this.password = password;
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
     * @return User
     */
    public User setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets phone value
     *
     * @return String
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Sets phone value
     *
     * @param phone
     * @return User
     */
    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Gets email value
     *
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets email value
     *
     * @param email
     * @return User
     */
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets employeeNumber value
     *
     * @return String
     */
    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    /**
     * Sets employeeNumber value
     *
     * @param employeeNumber
     * @return User
     */
    public User setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        return this;
    }

    /**
     * Gets locked value
     *
     * @return Boolean
     */
    public Boolean getLocked() {
        return this.locked;
    }

    /**
     * Sets locked value
     *
     * @param locked
     * @return User
     */
    public User setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    /**
     * Gets roles value
     *
     * @return Set
     */
    public Set<Role> getRoles() {
        return this.roles;
    }

    /**
     * Sets roles value
     *
     * @param roles
     * @return User
     */
    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    /**
     * Gets requestsCreated value
     *
     * @return Set
     */
    public Set<Request> getRequestsCreated() {
        return this.requestsCreated;
    }

    /**
     * Sets requestsCreated value
     *
     * @param requestsCreated
     * @return User
     */
    public User setRequestsCreated(Set<Request> requestsCreated) {
        this.requestsCreated = requestsCreated;
        return this;
    }

    /**
     * Gets requestsAssigned value
     *
     * @return Set
     */
    public Set<Request> getRequestsAssigned() {
        return this.requestsAssigned;
    }

    /**
     * Sets requestsAssigned value
     *
     * @param requestsAssigned
     * @return User
     */
    public User setRequestsAssigned(Set<Request> requestsAssigned) {
        this.requestsAssigned = requestsAssigned;
        return this;
    }

    /**
     * Gets client value
     *
     * @return Client
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * Sets client value
     *
     * @param client
     * @return User
     */
    public User setClient(Client client) {
        this.client = client;
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
     * @return User
     */
    public User setCreatedDate(Calendar createdDate) {
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
     * @return User
     */
    public User setCreatedBy(String createdBy) {
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
     * @return User
     */
    public User setModifiedDate(Calendar modifiedDate) {
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
     * @return User
     */
    public User setModifiedBy(String modifiedBy) {
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
        if (!(obj instanceof User)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((User) obj).getId());
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
        return "User {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", username='" + username + '\'' + ", password='" + password + '\'' + ", name='" + name + '\'' + ", phone='" + phone + '\'' + ", email='" + email + '\'' + ", employeeNumber='" + employeeNumber + '\'' + ", locked='" + locked + '\'' + ", createdDate='" + createdDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(createdDate.getTime()) + '\'' + ", createdBy='" + createdBy + '\'' + ", modifiedDate='" + modifiedDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(modifiedDate.getTime()) + '\'' + ", modifiedBy='" + modifiedBy + '\'' + "}" + super.toString();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param rolesToAdd
     */
    public void addToRoles(Iterable<Role> rolesToAdd) {
        Assert.notNull(rolesToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Role item : rolesToAdd) {
            this.roles.add(item);
            item.getUsers().add(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param rolesToRemove
     */
    public void removeFromRoles(Iterable<Role> rolesToRemove) {
        Assert.notNull(rolesToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Role item : rolesToRemove) {
            this.roles.remove(item);
            item.getUsers().remove(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestsCreatedToAdd
     */
    public void addToRequestsCreated(Iterable<Request> requestsCreatedToAdd) {
        Assert.notNull(requestsCreatedToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsCreatedToAdd) {
            this.requestsCreated.add(item);
            item.setUserCreated(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestsCreatedToRemove
     */
    public void removeFromRequestsCreated(Iterable<Request> requestsCreatedToRemove) {
        Assert.notNull(requestsCreatedToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsCreatedToRemove) {
            this.requestsCreated.remove(item);
            item.setUserCreated(null);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestsAssignedToAdd
     */
    public void addToRequestsAssigned(Iterable<Request> requestsAssignedToAdd) {
        Assert.notNull(requestsAssignedToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsAssignedToAdd) {
            this.requestsAssigned.add(item);
            item.setUserAssigned(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param requestsAssignedToRemove
     */
    public void removeFromRequestsAssigned(Iterable<Request> requestsAssignedToRemove) {
        Assert.notNull(requestsAssignedToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsAssignedToRemove) {
            this.requestsAssigned.remove(item);
            item.setUserAssigned(null);
        }
    }
}
