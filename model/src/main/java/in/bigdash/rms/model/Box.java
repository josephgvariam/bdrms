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

import in.bigdash.rms.model.inventory.BoxInventoryItem;
import io.springlets.format.EntityFormat;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Calendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
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
@Table(name = "BD_BOX")
@EntityFormat("#{barcode}")
@Audited
public class Box {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
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

    @NotAudited
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "box")
    private Set<File> files = new HashSet<File>();


    @OneToOne(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "box")
    @EntityFormat
    private BoxInventoryItem inventoryItem;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHELF_ID")
    @EntityFormat
    private Shelf shelf;


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


    public Box setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public Box setVersion(Long version) {
        this.version = version;
        return this;
    }


    public String getBarcode() {
        return this.barcode;
    }


    public Box setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }


    public String getLocation() {
        return this.location;
    }


    public Box setLocation(String location) {
        this.location = location;
        return this;
    }


    public Set<File> getFiles() {
        return this.files;
    }


    public Box setFiles(Set<File> files) {
        this.files = files;
        return this;
    }


    public BoxInventoryItem getInventoryItem() {
        return this.inventoryItem;
    }


    public Box setInventoryItem(BoxInventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }


    public Shelf getShelf() {
        return this.shelf;
    }


    public Box setShelf(Shelf shelf) {
        this.shelf = shelf;
        return this;
    }


    public Calendar getCreatedDate() {
        return this.createdDate;
    }


    public Box setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
        return this;
    }


    public String getCreatedBy() {
        return this.createdBy;
    }


    public Box setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }


    public Calendar getModifiedDate() {
        return this.modifiedDate;
    }


    public Box setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }


    public String getModifiedBy() {
        return this.modifiedBy;
    }


    public Box setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }


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


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "Box {" + "id='" + id + '\'' + ", barcode='" + barcode + '\'' + "}";
    }


    public void addToFiles(Iterable<File> filesToAdd) {
        Assert.notNull(filesToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (File item : filesToAdd) {
            this.files.add(item);
            item.setBox(this);
        }
    }


    public void removeFromFiles(Iterable<File> filesToRemove) {
        Assert.notNull(filesToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (File item : filesToRemove) {
            this.files.remove(item);
            item.setBox(null);
        }
    }


    public void addToInventoryItem(BoxInventoryItem inventoryItem) {
        if (inventoryItem == null) {
            removeFromInventoryItem();
        } else {
            this.inventoryItem = inventoryItem;
            inventoryItem.setBox(this);
        }
    }


    public void removeFromInventoryItem() {
        if (this.inventoryItem != null) {
            inventoryItem.setBox(null);
        }
        this.inventoryItem = null;
    }
}
