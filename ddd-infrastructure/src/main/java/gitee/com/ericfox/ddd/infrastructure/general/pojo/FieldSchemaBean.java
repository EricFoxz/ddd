package gitee.com.ericfox.ddd.infrastructure.general.pojo;

import gitee.com.ericfox.ddd.common.enums.contants.BooleanEnums;
import gitee.com.ericfox.ddd.common.enums.db.MySqlDataTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
public class FieldSchemaBean implements Serializable {
    /**
     * 数据类型
     */
    private MySqlDataTypeEnum dataType;

    /**
     * 长度
     */
    private int length;

    /**
     * 精度
     */
    private int scale;

    /**
     * 能否为空
     */
    private BooleanEnums.EnglishCode isNullable;

    /**
     * 默认值
     */
    private String[] defaultValue;

    /**
     * 备注
     */
    private String comment;
}
