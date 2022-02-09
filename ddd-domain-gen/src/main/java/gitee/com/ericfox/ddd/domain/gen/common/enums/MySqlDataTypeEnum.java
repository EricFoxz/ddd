package gitee.com.ericfox.ddd.domain.gen.common.enums;

import gitee.com.ericfox.ddd.domain.gen.model.TableMySqlBean;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BaseEnum;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ReUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * MySql数据类型枚举类
 */
public enum MySqlDataTypeEnum implements BaseEnum<MySqlDataTypeEnum, String> {
    TINYINT("tinyint", "", Integer.class),
    SMALLINT("smallint", "", Integer.class),
    MEDIUMINT("mediumint", "", Integer.class),
    INT("int", "", Long.class),
    INTEGER("integer", "", Long.class),
    BIGINT("bigint", "", Long.class),
    BIT("bit", "", Boolean.class),
    //REAL("real", ""),
    DOUBLE("double", "", Double.class),
    FLOAT("float", "", Float.class),
    DECIMAL("decimal", "", BigDecimal.class),
    NUMERIC("numeric", "", BigDecimal.class),
    CHAR("char", "", String.class),
    VARCHAR("varchar", "", String.class),
    DATE("date", "", Date.class),
    TIME("time", "", Time.class),
    YEAR("year", "", Date.class),
    TIMESTAMP("timestamp", "", Timestamp.class),
    DATETIME("datetime", "", Timestamp.class),
    TINYBLOB("tinyblob", "", byte[].class),
    BLOB("blob", "", byte[].class),
    MEDIUMBLOB("mediumblob", "", byte[].class),
    LONGBLOB("longblob", "", byte[].class),
    TINYTEXT("tinytext", "", String.class),
    TEXT("text", "", String.class),
    MEDIUMTEXT("mediumtext", "", String.class),
    LONGTEXT("longtext", "", String.class);
//    ENUM("enum", ""),
//    SET("set", ""),
//    BINARY("binary", ""),
//    VARBINARY("varbinary", ""),
//    POINT("point", ""),
//    LINESTRING("linestring", ""),
//    POLYGON("polygon", ""),
//    GEOMETRY("geometry", ""),
//    MULTIPOINT("multipoint", ""),
//    MULTILINESTRING("multilinestring", ""),
//    MULTIPOLYGON("multipolygon", ""),
//    GEOMETRYCOLLECTION("geometrycollection", ""),
//    JSON("json", "");

    private final String code;
    private final String description;
    private final Class<?> javaClass;

    MySqlDataTypeEnum(String code, String description, Class<?> javaClass) {
        this.code = code;
        this.description = description;
        this.javaClass = javaClass;
    }

    public static Class<?> getJavaClassByDataType(String code) {
        for (MySqlDataTypeEnum value : values()) {
            if (StrUtil.equalsIgnoreCase(value.code, code)) {
                return value.javaClass;
            }
        }
        return null;
    }

    public static Integer getLengthByColumn(TableMySqlBean.ColumnSchemaBean columnSchema) {
        if (columnSchema.getCharacter_maximum_length() != null) {
            return columnSchema.getCharacter_maximum_length();
        }
        return ReUtil.getFirstNumber(columnSchema.getColumn_type());
    }

    public static Integer getScaleByColumn(TableMySqlBean.ColumnSchemaBean columnSchema) {
        if (columnSchema.getNumeric_scale() != null) {
            return columnSchema.getNumeric_scale();
        }
        return 0;
    }

    @Override
    public String getName() {
        return this.getName();
    }

    @Override
    public String getCode() {
        return code;
    }

    public Class<?> getJavaClass() {
        return javaClass;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public MySqlDataTypeEnum[] getEnums() {
        return new MySqlDataTypeEnum[0];
    }
}
