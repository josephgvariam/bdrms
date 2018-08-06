package in.bigdash.rms.model.request;
import in.bigdash.rms.model.Facility;
import io.springlets.format.EntityFormat;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@EntityFormat
@Audited
@DiscriminatorValue("TRANSFER")
public class TransferRequest extends Request {


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";

    @NotNull
    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "FROM_FACILITY_ID")
    @EntityFormat
    private Facility fromFacility;

    @NotNull
    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "TO_FACILITY_ID")
    @EntityFormat
    private Facility toFacility;

    public Facility getFromFacility() {
        return fromFacility;
    }

    public void setFromFacility(Facility fromFacility) {
        this.fromFacility = fromFacility;
    }

    public Facility getToFacility() {
        return toFacility;
    }

    public void setToFacility(Facility toFacility) {
        this.toFacility = toFacility;
    }
}
