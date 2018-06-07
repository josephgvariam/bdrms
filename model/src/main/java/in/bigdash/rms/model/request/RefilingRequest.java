package in.bigdash.rms.model.request;
import io.springlets.format.EntityFormat;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@EntityFormat
@Audited
@DiscriminatorValue("REFILING")
public class RefilingRequest extends Request {
}
