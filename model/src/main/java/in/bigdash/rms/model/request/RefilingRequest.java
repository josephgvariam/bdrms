package in.bigdash.rms.model.request;
import io.springlets.format.EntityFormat;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;


@Entity
@EntityFormat
@Audited
public class RefilingRequest extends Request {
}
