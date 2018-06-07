package in.bigdash.rms.model.request;
import javax.persistence.*;
import java.util.Calendar;
import javax.validation.constraints.Future;

import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Min;
import org.springframework.format.annotation.NumberFormat;
import io.springlets.format.EntityFormat;


@Entity
@EntityFormat
@Audited
@DiscriminatorValue("PICKUP")
public class PickupRequest extends Request {


    @Column(name = "DOCUMENT_TYPE")
    private String documentType;


    @Column(name = "PICKUP_DATE_TIME")
    @Future
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm a")
    private Calendar pickupDateTime;


    @Min(0L)
    @NumberFormat
    private Integer numberFiles;


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public String getDocumentType() {
        return this.documentType;
    }


    public PickupRequest setDocumentType(String documentType) {
        this.documentType = documentType;
        return this;
    }


    public Calendar getPickupDateTime() {
        return this.pickupDateTime;
    }


    public PickupRequest setPickupDateTime(Calendar pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
        return this;
    }


    public Integer getNumberFiles() {
        return this.numberFiles;
    }


    public PickupRequest setNumberFiles(Integer numberFiles) {
        this.numberFiles = numberFiles;
        return this;
    }


    public String toString() {
        return "PickupRequest {" + "documentType='" + documentType + '\'' + ", pickupDateTime='" + pickupDateTime == null ? null : java.text.DateFormat.getDateTimeInstance().format(pickupDateTime.getTime()) + '\'' + ", numberFiles='" + numberFiles + '\'' + "}" + super.toString();
    }
}
