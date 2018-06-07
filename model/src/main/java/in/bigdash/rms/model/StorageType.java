package in.bigdash.rms.model;
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

import io.springlets.format.EntityFormat;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.util.Assert;


@Entity
@Table(name = "BD_STORAGE_TYPE")
@EntityFormat("#{name}")
public class StorageType {


    @Id
    @SequenceGenerator(name = "storageTypeGen", sequenceName = "BD_STORAGE_TYPE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storageTypeGen")
    @Column(name = "ID")
    private Long id;


    @Version
    @Column(name = "VERSION")
    private Long version;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bd_client_storage_types", joinColumns = { @JoinColumn(name = "storage_type_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "client_id", referencedColumnName = "id") })
    private Set<Client> clients = new HashSet<Client>();


    @NotNull
    @Column(name = "NAME")
    private String name;


    @Column(name = "DESCRIPTION")
    private String description;


    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "storageType")
    private Set<Request> requests = new HashSet<Request>();


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public Long getId() {
        return this.id;
    }


    public StorageType setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public StorageType setVersion(Long version) {
        this.version = version;
        return this;
    }


    public Set<Client> getClients() {
        return this.clients;
    }


    public StorageType setClients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }


    public String getName() {
        return this.name;
    }


    public StorageType setName(String name) {
        this.name = name;
        return this;
    }


    public String getDescription() {
        return this.description;
    }


    public StorageType setDescription(String description) {
        this.description = description;
        return this;
    }


    public Set<Request> getRequests() {
        return this.requests;
    }


    public StorageType setRequests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }


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


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "StorageType {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + "}" + super.toString();
    }


    public void addToRequests(Iterable<Request> requestsToAdd) {
        Assert.notNull(requestsToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsToAdd) {
            this.requests.add(item);
            item.setStorageType(this);
        }
    }


    public void removeFromRequests(Iterable<Request> requestsToRemove) {
        Assert.notNull(requestsToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Request item : requestsToRemove) {
            this.requests.remove(item);
            item.setStorageType(null);
        }
    }
}
