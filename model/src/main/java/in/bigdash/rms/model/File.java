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
import in.bigdash.rms.model.inventory.FileInventoryItem;
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
 * = File
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity(table = "BD_FILE", entityFormatExpression = "#{barcode}")
@RooEquals(isJpaEntity = true)
@RooJpaAudit
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_FILE")
@EntityFormat("#{barcode}")
public class File {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Id
    @SequenceGenerator(name = "fileGen", sequenceName = "BD_FILE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fileGen")
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
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "file")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<Document> documents = new HashSet<Document>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToOne(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "file")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    @EntityFormat
    private FileInventoryItem inventoryItem;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOX_ID")
    @EntityFormat
    private Box box;

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
     * @return File
     */
    public File setId(Long id) {
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
     * @return File
     */
    public File setVersion(Long version) {
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
     * @return File
     */
    public File setBarcode(String barcode) {
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
     * @return File
     */
    public File setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Gets documents value
     *
     * @return Set
     */
    public Set<Document> getDocuments() {
        return this.documents;
    }

    /**
     * Sets documents value
     *
     * @param documents
     * @return File
     */
    public File setDocuments(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    /**
     * Gets inventoryItem value
     *
     * @return FileInventoryItem
     */
    public FileInventoryItem getInventoryItem() {
        return this.inventoryItem;
    }

    /**
     * Sets inventoryItem value
     *
     * @param inventoryItem
     * @return File
     */
    public File setInventoryItem(FileInventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }

    /**
     * Gets box value
     *
     * @return Box
     */
    public Box getBox() {
        return this.box;
    }

    /**
     * Sets box value
     *
     * @param box
     * @return File
     */
    public File setBox(Box box) {
        this.box = box;
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
     * @return File
     */
    public File setCreatedDate(Calendar createdDate) {
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
     * @return File
     */
    public File setCreatedBy(String createdBy) {
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
     * @return File
     */
    public File setModifiedDate(Calendar modifiedDate) {
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
     * @return File
     */
    public File setModifiedBy(String modifiedBy) {
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
        if (!(obj instanceof File)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((File) obj).getId());
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
        return "File {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", barcode='" + barcode + '\'' + ", location='" + location + '\'' + ", createdDate='" + createdDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(createdDate.getTime()) + '\'' + ", createdBy='" + createdBy + '\'' + ", modifiedDate='" + modifiedDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(modifiedDate.getTime()) + '\'' + ", modifiedBy='" + modifiedBy + '\'' + "}" + super.toString();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentsToAdd
     */
    public void addToDocuments(Iterable<Document> documentsToAdd) {
        Assert.notNull(documentsToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Document item : documentsToAdd) {
            this.documents.add(item);
            item.setFile(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param documentsToRemove
     */
    public void removeFromDocuments(Iterable<Document> documentsToRemove) {
        Assert.notNull(documentsToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Document item : documentsToRemove) {
            this.documents.remove(item);
            item.setFile(null);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param inventoryItem
     */
    public void addToInventoryItem(FileInventoryItem inventoryItem) {
        if (inventoryItem == null) {
            removeFromInventoryItem();
        } else {
            this.inventoryItem = inventoryItem;
            inventoryItem.setFile(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     */
    public void removeFromInventoryItem() {
        if (this.inventoryItem != null) {
            inventoryItem.setFile(null);
        }
        this.inventoryItem = null;
    }
}
