package gitee.com.ericfox.ddd.infrastructure.general.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
public class TableIndexesBean implements Serializable {
    private TableIndexBean[] value;
}
