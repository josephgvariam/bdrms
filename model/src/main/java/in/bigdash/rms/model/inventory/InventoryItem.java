package in.bigdash.rms.model.inventory;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import in.bigdash.rms.model.request.Request;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import io.springlets.format.EntityFormat;
import java.util.Objects;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * = InventoryItem
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity(inheritanceType = "SINGLE_TABLE", table = "BD_INVENTORY_ITEM", entityFormatExpression = "#{id}")
@RooEquals(isJpaEntity = true)
@Entity
@Table(name = "BD_INVENTORY_ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@EntityFormat("#{id}")
public class InventoryItem {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Id
    @SequenceGenerator(name = "inventoryItemGen", sequenceName = "BD_INVENTORY_ITEM_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventoryItemGen")
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
    @Column(name = "REF1")
    private String ref1;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "REF2")
    private String ref2;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "REF3")
    private String ref3;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "REF4")
    private String ref4;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "REF5")
    private String ref5;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private InventoryItemStatus status;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bd_request_inventory_item", joinColumns = { @JoinColumn(name = "inventory_item_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "request_id", referencedColumnName = "id") })
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
     * @return InventoryItem
     */
    public InventoryItem setId(Long id) {
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
     * @return InventoryItem
     */
    public InventoryItem setVersion(Long version) {
        this.version = version;
        return this;
    }

    /**
     * Gets ref1 value
     *
     * @return String
     */
    public String getRef1() {
        return this.ref1;
    }

    /**
     * Sets ref1 value
     *
     * @param ref1
     * @return InventoryItem
     */
    public InventoryItem setRef1(String ref1) {
        this.ref1 = ref1;
        return this;
    }

    /**
     * Gets ref2 value
     *
     * @return String
     */
    public String getRef2() {
        return this.ref2;
    }

    /**
     * Sets ref2 value
     *
     * @param ref2
     * @return InventoryItem
     */
    public InventoryItem setRef2(String ref2) {
        this.ref2 = ref2;
        return this;
    }

    /**
     * Gets ref3 value
     *
     * @return String
     */
    public String getRef3() {
        return this.ref3;
    }

    /**
     * Sets ref3 value
     *
     * @param ref3
     * @return InventoryItem
     */
    public InventoryItem setRef3(String ref3) {
        this.ref3 = ref3;
        return this;
    }

    /**
     * Gets ref4 value
     *
     * @return String
     */
    public String getRef4() {
        return this.ref4;
    }

    /**
     * Sets ref4 value
     *
     * @param ref4
     * @return InventoryItem
     */
    public InventoryItem setRef4(String ref4) {
        this.ref4 = ref4;
        return this;
    }

    /**
     * Gets ref5 value
     *
     * @return String
     */
    public String getRef5() {
        return this.ref5;
    }

    /**
     * Sets ref5 value
     *
     * @param ref5
     * @return InventoryItem
     */
    public InventoryItem setRef5(String ref5) {
        this.ref5 = ref5;
        return this;
    }

    /**
     * Gets status value
     *
     * @return InventoryItemStatus
     */
    public InventoryItemStatus getStatus() {
        return this.status;
    }

    /**
     * Sets status value
     *
     * @param status
     * @return InventoryItem
     */
    public InventoryItem setStatus(InventoryItemStatus status) {
        this.status = status;
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
     * @return InventoryItem
     */
    public InventoryItem setRequests(Set<Request> requests) {
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
        if (!(obj instanceof InventoryItem)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((InventoryItem) obj).getId());
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
        return "InventoryItem {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", ref1='" + ref1 + '\'' + ", ref2='" + ref2 + '\'' + ", ref3='" + ref3 + '\'' + ", ref4='" + ref4 + '\'' + ", ref5='" + ref5 + '\'' + "}" + super.toString();
    }
}
