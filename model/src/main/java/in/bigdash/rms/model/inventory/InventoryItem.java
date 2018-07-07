package in.bigdash.rms.model.inventory;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import in.bigdash.rms.model.Client;
import in.bigdash.rms.model.User;
import in.bigdash.rms.model.request.Request;
import java.util.HashSet;
import java.util.Set;

import io.springlets.format.EntityFormat;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Objects;


@Entity
@Table(name = "BD_INVENTORY_ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING, length=20)
@EntityFormat("#{id}")
@Audited
public class InventoryItem {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;


    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "TYPE", insertable = false, updatable = false)
    private String type;

    @NotNull
    @Column(name = "REF1")
    private String ref1;


    @Column(name = "REF2")
    private String ref2;


    @Column(name = "REF3")
    private String ref3;


    @Column(name = "REF4")
    private String ref4;


    @Column(name = "REF5")
    private String ref5;


    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private InventoryItemStatus status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_CREATED_ID")
    @EntityFormat
    private User userCreated;

    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "inventoryItems")
    private Set<Request> requests = new HashSet<Request>();


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public Long getId() {
        return this.id;
    }


    public InventoryItem setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public InventoryItem setVersion(Long version) {
        this.version = version;
        return this;
    }


    public String getRef1() {
        return this.ref1;
    }


    public InventoryItem setRef1(String ref1) {
        this.ref1 = ref1;
        return this;
    }


    public String getRef2() {
        return this.ref2;
    }


    public InventoryItem setRef2(String ref2) {
        this.ref2 = ref2;
        return this;
    }


    public String getRef3() {
        return this.ref3;
    }


    public InventoryItem setRef3(String ref3) {
        this.ref3 = ref3;
        return this;
    }


    public String getRef4() {
        return this.ref4;
    }


    public InventoryItem setRef4(String ref4) {
        this.ref4 = ref4;
        return this;
    }


    public String getRef5() {
        return this.ref5;
    }


    public InventoryItem setRef5(String ref5) {
        this.ref5 = ref5;
        return this;
    }


    public InventoryItemStatus getStatus() {
        return this.status;
    }


    public InventoryItem setStatus(InventoryItemStatus status) {
        this.status = status;
        return this;
    }


    public Set<Request> getRequests() {
        return this.requests;
    }


    public InventoryItem setRequests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public Client getClient()
    {
        return this.userCreated.getClient();
    }

    public String getDepartment()
    {
        return this.userCreated.getClient().getDepartment();
    }

    public String getType() {
        return type;
    }

    public String getDocumentBarcode()
    {
        return null;
    }

    public String getFileBarcode()
    {
        return null;
    }

    public String getBoxBarcode()
    {
        return null;
    }

    public String getShelfBarcode()
    {
        return null;
    }

    public String getLocation()
    {
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // instanceof is false if the instance is null
        if (!(obj instanceof InventoryItem)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((InventoryItem) obj).getId());
    }


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "InventoryItem {id=" + id + ", type=" + type + ", ref1=" + ref1 + "}";
    }
}
