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
import io.springlets.format.EntityFormat;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "BD_FACILITY")
@EntityFormat("#{name}")
@Audited
public class Facility {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;


    @Version
    @Column(name = "VERSION")
    private Long version;


    @NotNull
    @Column(name = "NAME")
    private String name;


    @NotNull
    @Column(name = "ADDRESS")
    private String address;


    @NotAudited
    @NotNull
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "facility")
    private Set<Shelf> shelves = new HashSet<Shelf>();


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


    public Facility setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public Facility setVersion(Long version) {
        this.version = version;
        return this;
    }


    public String getName() {
        return this.name;
    }


    public Facility setName(String name) {
        this.name = name;
        return this;
    }


    public String getAddress() {
        return this.address;
    }


    public Facility setAddress(String address) {
        this.address = address;
        return this;
    }


    public Set<Shelf> getShelves() {
        return this.shelves;
    }


    public Facility setShelves(Set<Shelf> shelves) {
        this.shelves = shelves;
        return this;
    }


    public Calendar getCreatedDate() {
        return this.createdDate;
    }


    public Facility setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
        return this;
    }


    public String getCreatedBy() {
        return this.createdBy;
    }


    public Facility setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }


    public Calendar getModifiedDate() {
        return this.modifiedDate;
    }


    public Facility setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }


    public String getModifiedBy() {
        return this.modifiedBy;
    }


    public Facility setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // instanceof is false if the instance is null
        if (!(obj instanceof Facility)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((Facility) obj).getId());
    }


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "Facility {id=" + id + ", name=" + name + "}";
    }


    public void addToShelves(Iterable<Shelf> shelvesToAdd) {
        Assert.notNull(shelvesToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Shelf item : shelvesToAdd) {
            this.shelves.add(item);
            item.setFacility(this);
        }
    }


    public void removeFromShelves(Iterable<Shelf> shelvesToRemove) {
        Assert.notNull(shelvesToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Shelf item : shelvesToRemove) {
            this.shelves.remove(item);
            item.setFacility(null);
        }
    }
}
