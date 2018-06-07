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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.roo.addon.jpa.annotations.entity.JpaRelationType;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaRelation;
import io.springlets.format.EntityFormat;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.util.Assert;

/**
 * = Shelf
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity(table = "BD_SHELF", entityFormatExpression = "#{barcode}")
@RooEquals(isJpaEntity = true)
@Entity
@Table(name = "BD_SHELF")
@EntityFormat("#{barcode}")
public class Shelf {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Id
    @SequenceGenerator(name = "shelfGen", sequenceName = "BD_SHELF_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shelfGen")
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
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ShelfStatus status;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @OneToMany(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "shelf")
    @RooJpaRelation(type = JpaRelationType.AGGREGATION)
    private Set<Box> boxes = new HashSet<Box>();

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID")
    @EntityFormat
    private Facility facility;

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
     * @return Shelf
     */
    public Shelf setId(Long id) {
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
     * @return Shelf
     */
    public Shelf setVersion(Long version) {
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
     * @return Shelf
     */
    public Shelf setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    /**
     * Gets status value
     *
     * @return ShelfStatus
     */
    public ShelfStatus getStatus() {
        return this.status;
    }

    /**
     * Sets status value
     *
     * @param status
     * @return Shelf
     */
    public Shelf setStatus(ShelfStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Gets boxes value
     *
     * @return Set
     */
    public Set<Box> getBoxes() {
        return this.boxes;
    }

    /**
     * Sets boxes value
     *
     * @param boxes
     * @return Shelf
     */
    public Shelf setBoxes(Set<Box> boxes) {
        this.boxes = boxes;
        return this;
    }

    /**
     * Gets facility value
     *
     * @return Facility
     */
    public Facility getFacility() {
        return this.facility;
    }

    /**
     * Sets facility value
     *
     * @param facility
     * @return Shelf
     */
    public Shelf setFacility(Facility facility) {
        this.facility = facility;
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
        if (!(obj instanceof Shelf)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((Shelf) obj).getId());
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
        return "Shelf {" + "id='" + id + '\'' + ", version='" + version + '\'' + ", barcode='" + barcode + '\'' + "}" + super.toString();
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxesToAdd
     */
    public void addToBoxes(Iterable<Box> boxesToAdd) {
        Assert.notNull(boxesToAdd, ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE);
        for (Box item : boxesToAdd) {
            this.boxes.add(item);
            item.setShelf(this);
        }
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param boxesToRemove
     */
    public void removeFromBoxes(Iterable<Box> boxesToRemove) {
        Assert.notNull(boxesToRemove, ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE);
        for (Box item : boxesToRemove) {
            this.boxes.remove(item);
            item.setShelf(null);
        }
    }
}
