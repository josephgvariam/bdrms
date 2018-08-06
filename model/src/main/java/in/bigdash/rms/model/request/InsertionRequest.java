package in.bigdash.rms.model.request;
import io.springlets.format.EntityFormat;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


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
}
