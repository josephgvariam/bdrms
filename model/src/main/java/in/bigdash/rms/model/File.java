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
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_FILE")
@EntityFormat("#{barcode}")
public class File {


    @Id
    @SequenceGenerator(name = "fileGen", sequenceName = "BD_FILE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fileGen")
    @Column(name = "ID")
    private Long id;


    @Version
    @Column(name = "VERSION")
    private Long version;


    @NotNull
    @Column(name = "BARCODE", unique = true)
    private String barcode;


    @Column(name = "LOCATION")
    private String location;


    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "file")
    private Set<Document> documents = new HashSet<Document>();


    @OneToOne(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "file")
    @EntityFormat
    private FileInventoryItem inventoryItem;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOX_ID")
    @EntityFormat
    private Box box;


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


    public File setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public File setVersion(Long version) {
        this.version = version;
        return this;
    }


    public String getBarcode() {
        return this.barcode;
    }


    public File setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }


    public String getLocation() {
        return this.location;
    }


    public File setLocation(String location) {
        this.location = location;
        return this;
    }


    public Set<Document> getDocuments() {
        return this.documents;
    }


    public File setDocuments(Set<Document> documents) {
        this.documents = documents;
        return this;
    }


    public FileInventoryItem getInventoryItem() {
        return this.inventoryItem;
    }


    public File setInventoryItem(FileInventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }


    public Box getBox() {
        return this.box;
    }


    public File setBox(Box box) {
        this.box = box;
        return this;
    }


    public Calendar getCreatedDate() {
        return this.createdDate;
    }


    public File setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
        return this;
    }


    public String getCreatedBy() {
        return this.createdBy;
    }


    public File setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }


    public Calendar getModifiedDate() {
        return this.modifiedDate;
    }


    public File setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }


    public String getModifiedBy() {
        return this.modifiedBy;
    }


    public File setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }


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


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "File {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", barcode='" + barcode + '\'' + ", location='" + location + '\'' + ", createdDate='" + createdDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(createdDate.getTime()) + '\'' + ", createdBy='" + createdBy + '\'' + ", modifiedDate='" + modifiedDate == null ? null : java.text.DateFormat.getDateTimeInstance().format(modifiedDate.getTime()) + '\'' + ", modifiedBy='" + modifiedBy + '\'' + "}" + super.toString();
    }


    public void addToDocuments(Iterable<Document> documentsToAdd) {
        Assert.notNull(documentsToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Document item : documentsToAdd) {
            this.documents.add(item);
            item.setFile(this);
        }
    }


    public void removeFromDocuments(Iterable<Document> documentsToRemove) {
        Assert.notNull(documentsToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Document item : documentsToRemove) {
            this.documents.remove(item);
            item.setFile(null);
        }
    }


    public void addToInventoryItem(FileInventoryItem inventoryItem) {
        if (inventoryItem == null) {
            removeFromInventoryItem();
        } else {
            this.inventoryItem = inventoryItem;
            inventoryItem.setFile(this);
        }
    }


    public void removeFromInventoryItem() {
        if (this.inventoryItem != null) {
            inventoryItem.setFile(null);
        }
        this.inventoryItem = null;
    }
}
