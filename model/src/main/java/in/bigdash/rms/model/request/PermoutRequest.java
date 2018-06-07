package in.bigdash.rms.model.request;
import io.springlets.format.EntityFormat;
import javax.persistence.Entity;


@Entity
@EntityFormat
public class PermoutRequest extends Request {


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public String toString() {
        return "PermoutRequest {" + "}" + super.toString();
    }
}
