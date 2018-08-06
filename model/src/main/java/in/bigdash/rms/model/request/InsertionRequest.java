package in.bigdash.rms.model.request;
import in.bigdash.rms.model.File;
import in.bigdash.rms.model.inventory.FileInventoryItem;
import in.bigdash.rms.model.inventory.InsertInventoryItem;
import in.bigdash.rms.model.inventory.InventoryItem;
import io.springlets.format.EntityFormat;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;


@Entity
@EntityFormat
@Audited
@DiscriminatorValue("INSERTION")
public class InsertionRequest extends Request {


    public static final String ITERABLE_TO_ADD_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";


    public static final String ITERABLE_TO_REMOVE_CANT_BE_NULL_MESSAGE = "The given Iterable of items to add can't be null!";

    @NotNull
    @Min(1L)
    @NumberFormat
    private Integer numberFiles;

    @Column(name = "INSERT_DOC_FILE_MAP", length = 1000)
    private String insertDocFileMapStr;

    public Integer getNumberFiles() {
        return numberFiles;
    }

    public void setNumberFiles(Integer numberFiles) {
        this.numberFiles = numberFiles;
    }

    public String getInsertDocFileMapStr() {
        return insertDocFileMapStr;
    }

    public void setInsertDocFileMapStr(String insertDocFileMapStr) {
        this.insertDocFileMapStr = insertDocFileMapStr;
    }

    public Set<InsertInventoryItem> getInsertInventoryItems(){
        Map<String, String> docFilesMap = new HashMap<>();

        if(!StringUtils.isEmpty(insertDocFileMapStr)){
            String[] xs = insertDocFileMapStr.split("&");
            for(String x : xs){
                String[] y = x.split("=");
                String docBarcode = y[0];
                String fileBarcode = y[1];

                docFilesMap.put(docBarcode, fileBarcode);
            }
        }

        Set<InsertInventoryItem> result = new HashSet<>();
        Map<String, FileInventoryItem> fileInventoryItemMap = new HashMap<>();

        for(InventoryItem i : getInventoryItems()){
            fileInventoryItemMap.put(i.getFileBarcode(), (FileInventoryItem)i );
        }

        for (Map.Entry<String, String> entry : docFilesMap.entrySet())
        {
            String docBarcode = entry.getKey();
            String fileBarcode = entry.getValue();

            FileInventoryItem fi = fileInventoryItemMap.get(fileBarcode);
            InsertInventoryItem insertInventoryItem = new InsertInventoryItem(docBarcode, fileBarcode, fi.getBoxBarcode(), fi.getShelfBarcode());

            result.add(insertInventoryItem);
        }

        return result;
    }
}
