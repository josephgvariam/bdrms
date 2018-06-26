package in.bigdash.rms.model;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import io.springlets.format.EntityFormat;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.util.Assert;


@Entity
@Table(name = "BD_SHELF")
@EntityFormat("#{barcode}")
@Audited
public class Shelf {


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


    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ShelfStatus status;

    @NotAudited
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "shelf")
    private Set<Box> boxes = new HashSet<Box>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    @EntityFormat
    private Facility facility;


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public Long getId() {
        return this.id;
    }


    public Shelf setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getVersion() {
        return this.version;
    }


    public Shelf setVersion(Long version) {
        this.version = version;
        return this;
    }


    public String getBarcode() {
        return this.barcode;
    }


    public Shelf setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }


    public ShelfStatus getStatus() {
        return this.status;
    }


    public Shelf setStatus(ShelfStatus status) {
        this.status = status;
        return this;
    }


    public Set<Box> getBoxes() {
        return this.boxes;
    }


    public Shelf setBoxes(Set<Box> boxes) {
        this.boxes = boxes;
        return this;
    }


    public Facility getFacility() {
        return this.facility;
    }


    public Shelf setFacility(Facility facility) {
        this.facility = facility;
        return this;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // instanceof is false if the instance is null
        if (!(obj instanceof Shelf)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((Shelf) obj).getId());
    }


    public int hashCode() {
        return 31;
    }


    public String toString() {
        return "Shelf {" + "id='" + id + '\'' + ", barcode='" + barcode + '\'' + "}";
    }


    public void addToBoxes(Iterable<Box> boxesToAdd) {
        Assert.notNull(boxesToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Box item : boxesToAdd) {
            this.boxes.add(item);
            item.setShelf(this);
        }
    }


    public void removeFromBoxes(Iterable<Box> boxesToRemove) {
        Assert.notNull(boxesToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Box item : boxesToRemove) {
            this.boxes.remove(item);
            item.setShelf(null);
        }
    }
}
