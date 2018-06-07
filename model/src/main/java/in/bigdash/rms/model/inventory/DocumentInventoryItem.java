package in.bigdash.rms.model.inventory;
import org.springframework.roo.addon.javabean.annotations.RooJavaBean;
import org.springframework.roo.addon.javabean.annotations.RooToString;
import org.springframework.roo.addon.jpa.annotations.entity.RooJpaEntity;
import in.bigdash.rms.model.Document;
import io.springlets.format.EntityFormat;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Entity;


@RooJavaBean
@RooToString
@RooJpaEntity
@Entity
@EntityFormat
public class DocumentInventoryItem extends InventoryItem {


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_ID")
    @EntityFormat
    private Document document;


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public Document getDocument() {
        return this.document;
    }


    public DocumentInventoryItem setDocument(Document document) {
        this.document = document;
        return this;
    }


    public String toString() {
        return "DocumentInventoryItem {" + "}" + super.toString();
    }
}
