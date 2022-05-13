package gitee.com.ericfox.ddd.common.enums.db;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;
import gitee.com.ericfox.ddd.common.toolkit.coding.NumberUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.ReUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * MySql数据类型枚举类
 */
@Getter
public enum MySqlDataTypeEnum implements BaseEnum<MySqlDataTypeEnum, String> {
    TINYINT("tinyint", "", Integer.class, null),
    SMALLINT("smallint", "", Integer.class, null),
    MEDIUMINT("mediumint", "", Integer.class, null),
    INT("int", "", Long.class, null),
    @Deprecated
    INTEGER("integer", "等价于int", Long.class, null),
    BIGINT("bigint", "", Long.class, null),
    BIT("bit", "", Boolean.class, null),
    //REAL("real", ""),
    DOUBLE("double", "", Double.class, null),
    FLOAT("float", "", Float.class, null),
    DECIMAL("decimal", "", BigDecimal.class, null),
    @Deprecated
    NUMERIC("numeric", "numeric就是decimal", BigDecimal.class, null),
    CHAR("char", "", String.class, null),
    VARCHAR("varchar", "", String.class, null),
    DATE("date", "", Date.class, 0L),
    TIME("time", "", Time.class, null),
    YEAR("year", "", Date.class, 4L),
    TIMESTAMP("timestamp", "", Timestamp.class, null),
    DATETIME("datetime", "", Timestamp.class, null),
    TINYBLOB("tinyblob", "", byte[].class, 0L),
    BLOB("blob", "", byte[].class, 0L),
    MEDIUMBLOB("mediumblob", "", byte[].class, 0L),
    LONGBLOB("longblob", "", byte[].class, 0L),
    TINYTEXT("tinytext", "", String.class, 255L),
    TEXT("text", "", String.class, 65535L),
    MEDIUMTEXT("mediumtext", "", String.class, 16777215L),
    LONGTEXT("longtext", "", String.class, 4294967295L),
    @Deprecated
    ENUM("enum", "枚举类型，对数据迁移不友好，不推荐", String.class, 0L),
    @Deprecated
    SET("set", "集合类型", String[].class, 0L),
    @Deprecated
    BINARY("binary", "支持长度上限为256，推荐用blob或text类型", byte[].class, null),
    @Deprecated
    VARBINARY("varbinary", "支持长度范围为-1至10485760", String.class, null),
    @Deprecated
    POINT("point", "", String.class, 0L),
    @Deprecated
    LINESTRING("linestring", "", String.class, 0L),
    @Deprecated
    POLYGON("polygon", "", String.class, 0L),
    @Deprecated
    GEOMETRY("geometry", "地理位置类型，不常见", String.class, 0L),
    @Deprecated
    MULTIPOINT("multipoint", "", String.class, 0L),
    @Deprecated
    MULTILINESTRING("multilinestring", "", String.class, 0L),
    @Deprecated
    MULTIPOLYGON("multipolygon", "", String.class, 0L),
    @Deprecated
    GEOMETRYCOLLECTION("geometrycollection", "混合数据类型，不常见", String.class, 0L),
    @Deprecated
    JSON("json", "会变成longtext + 执行json格式检查，设置为NULL的时候好像会有问题", String.class, 4294967295L);

    /**
     * code名称
     */
    private final String code;
    /**
     * 说明
     */
    private final String comment;
    /**
     * 反射java类型
     */
    private final Class<?> javaClass;
    /**
     * 固定长度
     */
    private final Long length;

    MySqlDataTypeEnum(String code, String comment, Class<?> javaClass, Long length) {
        this.code = code;
        this.comment = comment;
        this.javaClass = javaClass;
        this.length = length;
    }

    public static Class<?> getJavaClassByDataType(String code) {
        for (MySqlDataTypeEnum value : values()) {
            if (StrUtil.equalsIgnoreCase(value.code, code)) {
                return value.javaClass;
            }
        }
        return null;
    }

    /**
     * 根据数据类型获取长度 如varchar(255) 返回255
     */
    public static int getLengthByColumnTypeString(String column_type) {
        return getLengthByColumnTypeString(column_type, null, null);
    }

    public static int getLengthByColumnTypeString(String column_type, Integer character_maximum_length, Integer numeric_precision) {
        if (character_maximum_length != null && character_maximum_length > 0) {
            return character_maximum_length;
        }
        if (numeric_precision != null && numeric_precision > 0) {
            return numeric_precision;
        }
        Integer firstNumber = ReUtil.getFirstNumber(column_type);
        if (firstNumber == null) {
            return 0;
        }
        return firstNumber;
    }

    /**
     * 根据类型、长度及精度拼接出 SQL数据类型
     */
    public static String getColumnTypeStringByInfo(String type, Integer characterMaximumLength, Integer numericPrecision, Integer numericScale, Integer datetimePrecision) {
        MySqlDataTypeEnum mySqlDataTypeEnum = MySqlDataTypeEnum.CHAR.getEnumByName(type);
        if (mySqlDataTypeEnum == null) {
            return type;
        }
        if (mySqlDataTypeEnum.getLength() != null) {
            return mySqlDataTypeEnum.getCode();
        }
        String lengthStr = (characterMaximumLength != null ? characterMaximumLength.toString() : numericPrecision != null ? numericPrecision + (numericScale != null ? "," + numericScale : "") : datetimePrecision != null ? datetimePrecision.toString() : "");
        if (StrUtil.isBlank(lengthStr)) {
            lengthStr = "(" + lengthStr + ")";
        }
        return mySqlDataTypeEnum.getCode() + lengthStr;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public MySqlDataTypeEnum[] getEnums() {
        return values();
    }
}
