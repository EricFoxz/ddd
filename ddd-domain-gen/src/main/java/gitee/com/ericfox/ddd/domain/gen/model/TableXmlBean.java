package gitee.com.ericfox.ddd.domain.gen.model;

import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.enums.MySqlDataTypeEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Slf4j
@ToString
public class TableXmlBean implements GenLogger {
    private MetaBean meta = new MetaBean();
    private DataBean data = new DataBean();

    @Data
    public static class MetaBean {
        /**
         * 注释
         */
        private String tableComment;
        /**
         * 领域名称
         */
        private String domainName;
        /**
         * 表名
         */
        private String tableName;
        /**
         * 持久化策略
         */
        private RepoTypeStrategyEnum repoTypeStrategyEnum;
        /**
         * 类名
         */
        private String className;
        private String class_name;
        private String ClassName;
        /**
         * 主键
         */
        private String idField;
        /**
         * 字段及类型
         */
        private Map<String, Class<?>> fieldClassMap = MapUtil.newLinkedHashMap();
        /**
         * 字段及长度
         */
        private Map<String, Integer> fieldLengthMap = MapUtil.newLinkedHashMap();
        /**
         * 字段及注释
         */
        private Map<String, String> fieldCommentMap = MapUtil.newLinkedHashMap();
        /**
         * 索引
         */
        private List<String> indexList = CollUtil.newArrayList();

        private void setFieldLengthMap() {
        }

        @Deprecated
        private void setClass_name(String class_name) {
            setClassName(StrUtil.toCamelCase(class_name));
        }

        public void setClassName(String className) {
            this.className = StrUtil.toCamelCase(className);
            this.ClassName = StrUtil.upperFirst(this.className);
            this.class_name = StrUtil.toUnderlineCase(this.className);
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = BeanUtil.beanToMap(this, false, true);
            map.put("ClassName", this.ClassName);
            return map;
        }
    }

    @Data
    public static class DataBean {
        /**
         * 默认值
         */
        private Map<String, Serializable> defaultValueMap = MapUtil.newLinkedHashMap();
    }

    /**
     * 从xml文件加载bean
     */
    public static TableXmlBean load(TableMySqlBean mySqlBean) {
        TableXmlBean xmlBean = new TableXmlBean();
        String tableName = mySqlBean.getTable_name();
        String domainName = StrUtil.contains(tableName, '_') ? StrUtil.splitToArray(tableName, '_', -1)[0] : "_unknown";
        MetaBean meta = xmlBean.getMeta();
        meta.setTableComment(StrUtil.isBlank(mySqlBean.getTable_comment()) ? mySqlBean.getTable_name() : mySqlBean.getTable_comment());
        meta.setTableName(tableName);
        meta.setDomainName(domainName);
        mySqlBean.getColumnSchemaList().forEach(columnSchema -> {
            String column_name = columnSchema.getColumn_name();
            if ("PRI".equals(columnSchema.getColumn_key())) { //主键
                meta.setIdField(column_name);
            }
            meta.getFieldClassMap().put(column_name, MySqlDataTypeEnum.getJavaClassByCode(columnSchema.getData_type()));
            if (columnSchema.getCharacter_maximum_length() != null) {
                meta.getFieldLengthMap().put(column_name, columnSchema.getCharacter_maximum_length());
            } else {
                meta.getFieldLengthMap().put(column_name, ReUtil.getFirstNumber(columnSchema.getColumn_type()));
            }
            meta.getFieldCommentMap().put(column_name, columnSchema.getColumn_comment());
            meta.setRepoTypeStrategyEnum(RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY);
            meta.setDomainName(domainName);
            meta.setClassName(tableName);
        });
        DataBean data = xmlBean.getData();
        return xmlBean;
    }

    public static TableXmlBean load(File file) {
        return XmlUtil.readObjectFromXml(file);
    }

    public TableXmlBean() {
    }

    public Document toDocument() {
        return XmlUtil.beanToXml(this);
    }
}
