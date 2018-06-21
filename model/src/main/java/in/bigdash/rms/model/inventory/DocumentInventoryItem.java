package in.bigdash.rms.model.inventory;
import in.bigdash.rms.model.Document;
import io.springlets.format.EntityFormat;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@EntityFormat
@Audited
@DiscriminatorValue("DOCUMENT")
public class DocumentInventoryItem extends InventoryItem {

    @NotNull
    @OneToOne(cascade = { javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.PERSIST }, fetch = FetchType.EAGER)
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

    @Override
    public String getDocumentBarcode() {
        return document != null ? document.getBarcode() : null;
    }

    @Override
    public String getFileBarcode() {
        return document != null && document.getFile() != null ? document.getFile().getBarcode() : null;
    }

    @Override
    public String getBoxBarcode() {
        return document != null && document.getFile() != null && document.getFile().getBox() != null ? document.getFile().getBox().getBarcode() : null;
    }

    @Override
    public String getShelfBarcode() {
        return document != null && document.getFile() != null && document.getFile().getBox() != null && document.getFile().getBox().getShelf() != null ? document.getFile().getBox().getShelf().getBarcode() : null;
    }

    @Override
    public String getLocation() {
        return document != null ? document.getLocation() : null;
    }
}
