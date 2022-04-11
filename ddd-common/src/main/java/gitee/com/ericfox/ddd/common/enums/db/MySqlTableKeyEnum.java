package gitee.com.ericfox.ddd.common.enums.db;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;
import lombok.Getter;

/**
 * MySql键类型枚举类
 */
@Getter
public enum MySqlTableKeyEnum implements BaseEnum<MySqlTableKeyEnum, String> {
    PRIMARY_KEY("primaryKey", "主键"),
    @Deprecated
    FOREIGN_KEY("foreignKey", "外键，不推荐使用外键，会使迁移性、效率等方方面面受影响");
    private final String code;
    private final String comment;

    MySqlTableKeyEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public MySqlTableKeyEnum[] getEnums() {
        return values();
    }
}
