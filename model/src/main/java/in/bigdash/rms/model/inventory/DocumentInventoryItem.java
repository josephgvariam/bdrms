package in.bigdash.rms.model.inventory;
import in.bigdash.rms.model.Document;
import io.springlets.format.EntityFormat;
import org.hibernate.envers.Audited;

import javax.persistence.*;


@Entity
@EntityFormat
@Audited
@DiscriminatorValue("DOCUMENT")
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
