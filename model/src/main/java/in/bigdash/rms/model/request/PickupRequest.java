package in.bigdash.rms.model.request;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import javax.persistence.Column;
import java.util.Calendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.NumberFormat;
import io.springlets.format.EntityFormat;
import javax.persistence.Entity;

/**
 * = PickupRequest
 *
 * TODO Auto-generated class documentation
 *
 */
@RooJavaBean
@RooToString
@RooJpaEntity
@Entity
@EntityFormat
public class PickupRequest extends Request {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "DOCUMENT_TYPE")
    private String documentType;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Column(name = "PICKUP_DATE_TIME")
    @Future
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm a")
    private Calendar pickupDateTime;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    @Min(0L)
    @NumberFormat
    private Integer numberFiles;

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
     * Gets documentType value
     *
     * @return String
     */
    public String getDocumentType() {
        return this.documentType;
    }

    /**
     * Sets documentType value
     *
     * @param documentType
     * @return PickupRequest
     */
    public PickupRequest setDocumentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    /**
     * Gets pickupDateTime value
     *
     * @return Calendar
     */
    public Calendar getPickupDateTime() {
        return this.pickupDateTime;
    }

    /**
     * Sets pickupDateTime value
     *
     * @param pickupDateTime
     * @return PickupRequest
     */
    public PickupRequest setPickupDateTime(Calendar pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
        return this;
    }

    /**
     * Gets numberFiles value
     *
     * @return Integer
     */
    public Integer getNumberFiles() {
        return this.numberFiles;
    }

    /**
     * Sets numberFiles value
     *
     * @param numberFiles
     * @return PickupRequest
     */
    public PickupRequest setNumberFiles(Integer numberFiles) {
        this.numberFiles = numberFiles;
        return this;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return String
     */
    public String toString() {
        return "PickupRequest {" + "documentType='" + documentType + '\'' + ", pickupDateTime='" + pickupDateTime == null ? null : java.text.DateFormat.getDateTimeInstance().format(pickupDateTime.getTime()) + '\'' + ", numberFiles='" + numberFiles + '\'' + "}" + super.toString();
    }
}
