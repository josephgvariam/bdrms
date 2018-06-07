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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import io.springlets.format.EntityFormat;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "BD_ROLE")
@EntityFormat("#{name}")
public class Role {


    @Id
    @SequenceGenerator(name = "roleGen", sequenceName = "BD_ROLE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleGen")
    @Column(name = "ID")
    private Long id;


    @Version
    @Column(name = "VERSION")
    private Long version;


    @NotNull
    @Column(name = "NAME", unique = true)
    private String name;


    @Column(name = "DESCRIPTION")
    private String description;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bd_user_role", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
    private Set<User> users = new HashSet<User>();


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public Long getId() {
        return this.id;
    }


    public Role setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public Role setVersion(Long version) {
        this.version = version;
        return this;
    }


    public String getName() {
        return this.name;
    }


    public Role setName(String name) {
        this.name = name;
        return this;
    }


    public String getDescription() {
        return this.description;
    }


    public Role setDescription(String description) {
        this.description = description;
        return this;
    }


    public Set<User> getUsers() {
        return this.users;
    }


    public Role setUsers(Set<User> users) {
        this.users = users;
        return this;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // instanceof is false if the instance is null
        if (!(obj instanceof Role)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((Role) obj).getId());
    }


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "Role {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + "}" + super.toString();
    }
}
