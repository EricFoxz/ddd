package gitee.com.ericfox.ddd.common.enums.db;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum MySqlColumnKeyEnum implements BaseEnum<MySqlColumnKeyEnum, String> {
    PRI("PRI", "主键"),
    MUL("MUL", "非空");
    private final String code;
    private final String comment;

    MySqlColumnKeyEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public MySqlColumnKeyEnum[] getEnums() {
        return values();
    }
}
