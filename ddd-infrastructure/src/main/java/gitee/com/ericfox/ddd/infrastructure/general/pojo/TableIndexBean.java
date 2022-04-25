package gitee.com.ericfox.ddd.infrastructure.general.pojo;

import gitee.com.ericfox.ddd.common.enums.db.DbDataStructureEnum;
import gitee.com.ericfox.ddd.common.enums.db.DbIndexTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
public class TableIndexBean implements Serializable {
    /**
     * 索引名称
     */
    private String name;

    /**
     * 字段名
     */
    private String[] column;

    /**
     * 索引类型
     */
    private DbIndexTypeEnum dbIndexTypeEnum;

    /**
     * 数据结构
     */
    private DbDataStructureEnum dbDataTypeEnum;
}
