package gitee.com.ericfox.ddd.domain.gen.bean;

import gitee.com.ericfox.ddd.common.enums.contants.BooleanEnums;
import gitee.com.ericfox.ddd.common.enums.db.DbIndexTypeEnum;
import gitee.com.ericfox.ddd.common.enums.db.MySqlColumnKeyEnum;
import gitee.com.ericfox.ddd.common.enums.db.MySqlDataTypeEnum;
import gitee.com.ericfox.ddd.common.enums.db.MySqlTableEngineEnum;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.infrastructure.general.pojo.FieldSchemaBean;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Mysql表 bean
 */
@Getter
@Setter
public class TableMySqlBean {
    private String table_catalog;
    private String table_schema;
    private String table_name;
    private String table_type;
    private MySqlTableEngineEnum engine;
    private Long version;
    private String row_format;
    private Long table_rows;
    private Long avg_row_length;
    private Long data_length;
    private Long max_data_length;
    private Long index_length;
    private Long data_free;
    private Long auto_increment;
    private Date create_time;
    private Date update_time;
    private Date check_time;
    private String table_collation;
    private Long checksum;
    private String create_options;
    private String table_comment;
    private Long max_index_length;
    private Character temporary;

    private List<ColumnSchemaBean> columnSchemaList = CollUtil.newArrayList();
    private List<IndexSchemaBean> indexSchemaList = CollUtil.newArrayList();

    @Getter
    @Setter
    public static class ColumnSchemaBean {
        private String table_catalog;
        private String table_schema;
        private String table_name;
        private String column_name;
        private Integer ordinal_position;
        private String column_default;
        private String is_nullable;
        private String data_type;
        private Integer character_maximum_length;
        private Integer character_octet_length;
        private Integer numeric_precision;
        private Integer numeric_scale;
        private Integer datetime_precision;
        private String character_set_name;
        private String collation_name;
        private String column_type;
        private String column_key;
        private String extra;
        private String privileges;
        private String column_comment;
        private String is_generated;
        private String generation_expression;
    }

    @Getter
    @Setter
    public static class IndexSchemaBean {
        private String table;
        private BooleanEnums.NumberCode non_unique;
        private String key_name;
        private Integer seq_in_index;
        private String column_name;
        private String collation;
        private String cardinality;
        private String sun_part;
        private String packed;
        private BooleanEnums.EnglishCode Null;
        private DbIndexTypeEnum index_type;
        private String comment;
        private String index_comment;

        public void setNon_unique(Integer non_unique) {
            this.non_unique = BooleanEnums.NumberCode.YES.getEnumByCode(non_unique);
        }

        public void setNull(String n) {
            Null = BooleanEnums.EnglishCode.YES.getEnumByCode(n);
        }

        public void setIndex_type(String index_type) {
            this.index_type = DbIndexTypeEnum.NORMAL.getEnumByCode(index_type);
        }
    }

    public static TableMySqlBean load(TableXmlBean xmlBean) {
        final TableMySqlBean mySqlBean = new TableMySqlBean();
        // 表名
        mySqlBean.setTable_name(xmlBean.getMeta().getClass_name());
        // 编码集
        mySqlBean.setTable_collation(xmlBean.getMeta().getTableCollation());
        // 表注释
        mySqlBean.setTable_comment(xmlBean.getMeta().getTableComment());
        // 存储引擎一律使用InnoDB，用MySql用的就是事务
        mySqlBean.setEngine(MySqlTableEngineEnum.INNODB);
        AtomicInteger index = new AtomicInteger(0);
        xmlBean.getMeta().getFieldClassMap().forEach((key, value) -> {
            int i = index.incrementAndGet();
            FieldSchemaBean fieldSchemaBean = xmlBean.getMeta().getFieldSchemaMap().get(key);
            ColumnSchemaBean columnSchemaBean = new ColumnSchemaBean();
            columnSchemaBean.setTable_catalog("def");
            // TODO 库名，暂时不管，库名的约束会增加复杂度，待细化
            // columnSchemaBean.setTable_schema();
            // 表名
            columnSchemaBean.setTable_name(xmlBean.getMeta().getClass_name());
            // 字段名
            columnSchemaBean.setColumn_name(StrUtil.toUnderlineCase(key));
            // 列的序号（123递增）
            columnSchemaBean.setOrdinal_position(i);
            // 默认值
            if (xmlBean.getData().getDefaultValueMap().get(key) == null) {
                columnSchemaBean.setColumn_default("NULL");
            } else {
                columnSchemaBean.setColumn_default("'" + xmlBean.getData().getDefaultValueMap().get(key).toString() + "'");
            }
            // 是否为空
            columnSchemaBean.setIs_nullable(fieldSchemaBean.getIsNullable().getCode());
            columnSchemaBean.setData_type(fieldSchemaBean.getDataType().getCode());
            Class<?> javaClass = fieldSchemaBean.getDataType().getJavaClass();
            if (CharSequence.class.isAssignableFrom(javaClass)) { //字符串
                // 以字符为单位的最大长度
                columnSchemaBean.setCharacter_maximum_length(fieldSchemaBean.getLength());
                // 以字节为单位的最大长度
                columnSchemaBean.setCharacter_octet_length(fieldSchemaBean.getLength() >> 2);
            } else if (Number.class.isAssignableFrom(javaClass)) { // 数字
                columnSchemaBean.setNumeric_precision(fieldSchemaBean.getLength());
                columnSchemaBean.setNumeric_scale(fieldSchemaBean.getScale());
            } else if (Date.class.isAssignableFrom(javaClass) || java.sql.Date.class.isAssignableFrom(javaClass)) {
                columnSchemaBean.setDatetime_precision(fieldSchemaBean.getScale());
            }
            columnSchemaBean.setCharacter_set_name("utf8mb4");
            columnSchemaBean.setCollation_name("utf8mb4_general_ci");
            // 列类型
            columnSchemaBean.setColumn_type(MySqlDataTypeEnum.getColumnTypeStringByInfo(fieldSchemaBean.getDataType().getName(), columnSchemaBean.getCharacter_maximum_length(), columnSchemaBean.getNumeric_precision(), columnSchemaBean.getNumeric_scale(), columnSchemaBean.getDatetime_precision()));
            // 列键
            if (key.equals(xmlBean.getMeta().getIdField())) { //主键
                columnSchemaBean.setColumn_key(MySqlColumnKeyEnum.PRI.getCode());
            } else if (fieldSchemaBean.getIsNullable().equals(BooleanEnums.EnglishCode.NO)) { //非空
                columnSchemaBean.setColumn_key(MySqlColumnKeyEnum.MUL.getCode());
            } else {
                columnSchemaBean.setColumn_key("");
            }
            // 拓展事件 如 on update current_timestamp() 表示更新行时同时更新时间戳，暂不考虑
            columnSchemaBean.setExtra("");
            columnSchemaBean.setPrivileges("select,insert,update,references");
            // 列说明
            columnSchemaBean.setColumn_comment(fieldSchemaBean.getComment());
            columnSchemaBean.setIs_generated("NEVER");
            columnSchemaBean.setGeneration_expression(null);
            mySqlBean.getColumnSchemaList().add(columnSchemaBean);
        });
        return mySqlBean;
    }

    /**
     * 转换为sql语句
     */
    public String toSqlString(boolean includeDropSql) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> primaryKeyList = CollUtil.newArrayList();
        sb.append("-- ").append(table_name).append("\n");
        if (includeDropSql) {
            sb.append("DROP TABLE ").append(table_name).append(" IF EXISTS;\n");
        }
        sb.append("CREATE TABLE ").append(table_name).append(" (\n");
        {
            AtomicInteger index = new AtomicInteger(0);
            getColumnSchemaList().forEach(columnSchemaBean -> {
                int i = index.getAndIncrement();
                ColumnSchemaBean schemaBean = columnSchemaList.get(i);
                sb.append("  `").append(schemaBean.getTable_name()).append("` ").append(schemaBean.getColumn_type()).append(" DEFAULT ").append(schemaBean.getColumn_default()).append(" COMMENT ").append(schemaBean.getColumn_comment());
                if (i < columnSchemaList.size() - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            });

            columnSchemaList.forEach(columnSchemaBean -> {
                if (MySqlColumnKeyEnum.PRI.getCode().equals(columnSchemaBean.getColumn_key())) {
                    primaryKeyList.add("`" + columnSchemaBean.getColumn_name() + "`");
                }
            });
        }
        sb.append("  PRIMARY KEY (").append(CollUtil.join(primaryKeyList, ",")).append(")");
        if (CollUtil.isNotEmpty(this.indexSchemaList)) {
            Map<String, List<IndexSchemaBean>> indexMap = MapUtil.newConcurrentHashMap();
            sb.append(",\n");
            indexSchemaList.forEach(indexSchemaBean -> {
                if (!"PRIMARY".equals(indexSchemaBean.getKey_name())) {
                    String key = indexSchemaBean.getKey_name();
                    if (indexMap.get(key) == null) {
                        indexMap.put(key, CollUtil.newArrayList(indexSchemaBean));
                    } else {
                        indexMap.get(key).add(indexSchemaBean);
                    }
                }
            });
            if (CollUtil.isNotEmpty(indexMap)) {
                AtomicInteger index = new AtomicInteger(0);
                indexMap.forEach((key, indexSchemaBeanList) -> {
                    sb.append("  KEY `").append(key).append("` (");
                    AtomicInteger index2 = new AtomicInteger(0);
                    StringJoiner stringJoiner = new StringJoiner(", ");
                    indexSchemaBeanList.forEach(indexSchemaBean -> {
                        stringJoiner.add("`" + indexSchemaBean.getColumn_name() + '`');
                    });
                    sb.append(stringJoiner).append(") USING ").append(indexSchemaBeanList.get(0).getIndex_type());
                    if (index.get() != indexSchemaList.size() - 1) {
                        sb.append(",");
                    }
                    sb.append("\n");
                    index.incrementAndGet();
                });
            }
        } else {
            sb.append("\n");
        }
        sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='").append(table_comment).append("';\n\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TableMySqlBean) {
            TableMySqlBean o1 = (TableMySqlBean) o;
            return this.getTable_name().equals(o1.getTable_name());
        }
        return false;
    }
}
