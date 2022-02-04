package gitee.com.ericfox.ddd.domain.gen.model;

import gitee.com.ericfox.ddd.domain.gen.GenLogger;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.XmlUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Slf4j
public class TableXmlBean implements GenLogger {
    private String comment;

    private MetaBean meta = new MetaBean();
    private DataBean data = new DataBean();

    @Data
    public static class MetaBean {
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
        private Map<String, String> fieldClassMap = MapUtil.newLinkedHashMap();
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
        xmlBean.setComment(StrUtil.isBlank(mySqlBean.getTable_comment()) ? tableName : mySqlBean.getTable_comment());
        MetaBean meta = xmlBean.getMeta();
        meta.setTableName(tableName);
        meta.setDomainName(domainName);
        mySqlBean.getColumnSchemaList().forEach(columnSchema -> {
            String column_name = columnSchema.getColumn_name();
            if ("PRI".equals(columnSchema.getColumn_key())) { //主键
                meta.setIdField(column_name);
            }
            meta.getFieldClassMap().put(column_name, columnSchema.getData_type());
            meta.getFieldLengthMap().put(column_name, columnSchema.getCharacter_maximum_length());
            meta.getFieldCommentMap().put(column_name, columnSchema.getColumn_comment());
            meta.setRepoTypeStrategyEnum(RepoTypeStrategyEnum.J_FINAL_REPO_STRATEGY);
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
