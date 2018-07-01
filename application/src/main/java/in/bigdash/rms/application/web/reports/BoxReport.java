package in.bigdash.rms.application.web.reports;

public class BoxReport implements Comparable<BoxReport>{
    private String barcode;
    private int numberFiles;
    private int numberDocuments;

    public BoxReport(String barcode, int numberFiles, int numberDocuments) {
        this.barcode = barcode;
        this.numberFiles = numberFiles;
        this.numberDocuments = numberDocuments;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getNumberFiles() {
        return numberFiles;
    }

    public int getNumberDocuments() {
        return numberDocuments;
    }

    @Override
    public int compareTo(BoxReport o) {
        return this.barcode.compareTo(o.barcode);
    }
}
