package in.bigdash.rms.model.request;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import io.springlets.format.EntityFormat;
import javax.persistence.Entity;


@RooJavaBean
@RooToString
@RooJpaEntity
@Entity
@EntityFormat
public class RefilingRequest extends Request {
}
