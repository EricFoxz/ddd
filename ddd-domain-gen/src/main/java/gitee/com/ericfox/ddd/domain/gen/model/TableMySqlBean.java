package gitee.com.ericfox.ddd.domain.gen.model;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private String engine;
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
        private String datetime_precision;
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
}
