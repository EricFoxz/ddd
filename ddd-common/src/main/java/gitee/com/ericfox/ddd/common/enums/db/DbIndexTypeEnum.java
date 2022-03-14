package gitee.com.ericfox.ddd.common.enums.db;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;

/**
 * 数据库索引类型枚举类
 */
public enum DbIndexTypeEnum implements BaseEnum<DbIndexTypeEnum, String> {
    NORMAL("normal", "普通索引"),
    @Deprecated
    UNIQUE("unique", "唯一索引"),
    FULL_TEXT("fullText", "全文索引");
    private final String code;
    private final String comment;

    DbIndexTypeEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public DbIndexTypeEnum[] getEnums() {
        return values();
    }
}
