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
import in.bigdash.rms.model.inventory.BoxInventoryItem;
import io.springlets.format.EntityFormat;
import javax.persistence.OneToOne;
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
 * = Box
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity(table = "BD_BOX", entityFormatExpression = "#{barcode}")
@RooEquals(isJpaEntity = true)
@RooJpaAudit
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_BOX")
@EntityFormat("#{barcode}")
public class Box {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Id
    @SequenceGenerator(name = "boxGen", sequenceName = "BD_BOX_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boxGen")
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
    @Column(name = "BARCODE", unique = true)
    private String barcode;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "LOCATION")
    private String location;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "box")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<File> files = new HashSet<File>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToOne(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "box")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    @EntityFormat
    private BoxInventoryItem inventoryItem;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHELF_ID")
    @EntityFormat
    private Shelf shelf;

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
     * @return Box
     */
    public Box setId(Long id) {
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
     * @return Box
     */
    public Box setVersion(Long version) {
        this.version = version;
        return this;
    }

    /**
     * Gets barcode value
     *
     * @return String
     */
    public String getBarcode() {
        return this.barcode;
    }

    /**
     * Sets barcode value
     *
     * @param barcode
     * @return Box
     */
    public Box setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    /**
     * Gets location value
     *
     * @return String
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Sets location value
     *
     * @param location
     * @return Box
     */
    public Box setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Gets files value
     *
     * @return Set
     */
    public Set<File> getFiles() {
        return this.files;
    }

    /**
     * Sets files value
     *
     * @param files
     * @return Box
     */
    public Box setFiles(Set<File> files) {
        this.files = files;
        return this;
    }

    /**
     * Gets inventoryItem value
     *
     * @return BoxInventoryItem
     */
    public BoxInventoryItem getInventoryItem() {
        return this.inventoryItem;
    }

    /**
     * Sets inventoryItem value
     *
     * @param inventoryItem
     * @return Box
     */
    public Box setInventoryItem(BoxInventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }

    /**
     * Gets shelf value
     *
     * @return Shelf
     */
    public Shelf getShelf() {
        return this.shelf;
    }

    /**
     * Sets shelf value
     *
     * @param shelf
     * @return Box
     */
    public Box setShelf(Shelf shelf) {
        this.shelf = shelf;
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
     * @return Box
     */
    public Box setCreatedDate(Calendar createdDate) {
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
     * @return Box
     */
    public Box setCreatedBy(String createdBy) {
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
     * @return Box
     */
    public Box setModifiedDate(Calendar modifiedDate) {
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
     * @return Box
     */
    public Box setModifiedBy(String modifiedBy) {
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
        if (!(obj instanceof Box)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((Box) obj).getId());
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
        return "Box {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", barcode='" + barcode + '\'' + ", location='" + location + '\'' + ", createdDate='" + createdDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(createdDate.getTime()) + '\'' + ", createdBy='" + createdBy + '\'' + ", modifiedDate='" + modifiedDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(modifiedDate.getTime()) + '\'' + ", modifiedBy='" + modifiedBy + '\'' + "}" + super.toString();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param filesToAdd
     */
    public void addToFiles(Iterable<File> filesToAdd) {
        Assert.notNull(filesToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (File item : filesToAdd) {
            this.files.add(item);
            item.setBox(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param filesToRemove
     */
    public void removeFromFiles(Iterable<File> filesToRemove) {
        Assert.notNull(filesToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (File item : filesToRemove) {
            this.files.remove(item);
            item.setBox(null);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItem
     */
    public void addToInventoryItem(BoxInventoryItem inventoryItem) {
        if (inventoryItem == null) {
            removeFromInventoryItem();
        } else {
            this.inventoryItem = inventoryItem;
            inventoryItem.setBox(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     */
    public void removeFromInventoryItem() {
        if (this.inventoryItem != null) {
            inventoryItem.setBox(null);
        }
        this.inventoryItem = null;
    }
}
