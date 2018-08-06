package in.bigdash.rms.model.inventory;

public class InsertInventoryItem {
    private String documentBarcode;
    private String fileBarcode;
    private String boxBarcode;
    private String shelfBarcode;

    public InsertInventoryItem() {

    }

    public InsertInventoryItem(String documentBarcode, String fileBarcode, String boxBarcode, String shelfBarcode) {
        this.documentBarcode = documentBarcode;
        this.fileBarcode = fileBarcode;
        this.boxBarcode = boxBarcode;
        this.shelfBarcode = shelfBarcode;
    }

    public String getDocumentBarcode() {
        return documentBarcode;
    }

    public void setDocumentBarcode(String documentBarcode) {
        this.documentBarcode = documentBarcode;
    }

    public String getFileBarcode() {
        return fileBarcode;
    }

    public void setFileBarcode(String fileBarcode) {
        this.fileBarcode = fileBarcode;
    }

    public String getBoxBarcode() {
        return boxBarcode;
    }

    public void setBoxBarcode(String boxBarcode) {
        this.boxBarcode = boxBarcode;
    }

    public String getShelfBarcode() {
        return shelfBarcode;
    }

    public void setShelfBarcode(String shelfBarcode) {
        this.shelfBarcode = shelfBarcode;
    }

}
