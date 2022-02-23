package gitee.com.ericfox.ddd.domain.gen.bean;

import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.interfaces.BasePo;
import gitee.com.ericfox.ddd.common.toolkit.coding.*;
import gitee.com.ericfox.ddd.domain.gen.common.GenLogger;
import gitee.com.ericfox.ddd.domain.gen.common.enums.MySqlDataTypeEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework.FieldComment;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework.FieldLength;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework.TableComment;
import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.service.RepoEnabledAnnotation;
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
         * 字段及精度
         */
        private Map<String, Integer> fieldScaleMap = MapUtil.newLinkedHashMap();
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
        meta.setClassName(tableName);
        meta.setDomainName(domainName);
        mySqlBean.getColumnSchemaList().forEach(columnSchema -> {
            String toCamelCase = StrUtil.toCamelCase(columnSchema.getColumn_name());
            if ("PRI".equals(columnSchema.getColumn_key())) { //主键
                meta.setIdField(toCamelCase);
            }
            meta.getFieldClassMap().put(toCamelCase, MySqlDataTypeEnum.getJavaClassByDataType(columnSchema.getData_type()));
            meta.getFieldLengthMap().put(toCamelCase, MySqlDataTypeEnum.getLengthByColumn(columnSchema));
            meta.getFieldScaleMap().put(toCamelCase, MySqlDataTypeEnum.getScaleByColumn(columnSchema));
            meta.getFieldCommentMap().put(toCamelCase, columnSchema.getColumn_comment());
            meta.setRepoTypeStrategyEnum(RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY);
        });
        DataBean data = xmlBean.getData();
        return xmlBean;
    }

    public static <PO extends BasePo<PO>> TableXmlBean load(TableJavaBean<PO> tableJava) {
        TableXmlBean tableXml = new TableXmlBean();
        MetaBean meta = tableXml.getMeta();
        TableComment tableCommentAnnotation = tableJava.getClazz().getAnnotation(TableComment.class);
        if (tableCommentAnnotation != null) {
            meta.setTableComment(tableCommentAnnotation.value());
        }
        meta.setDomainName(tableJava.getStructure().getDomainName());
        meta.setTableName(tableJava.getStructure().getTableName());
        meta.setClassName(tableJava.getStructure().getTableName());
        meta.setIdField(tableJava.getStructure().getId());
        RepoEnabledAnnotation repoEnabledAnnotation = tableJava.getClazz().getAnnotation(RepoEnabledAnnotation.class);
        if (repoEnabledAnnotation != null) {
            meta.setRepoTypeStrategyEnum(repoEnabledAnnotation.type());
        } else {
            meta.setRepoTypeStrategyEnum(RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY);
        }
        tableJava.getFieldList().forEach(field -> {
            String fieldName = field.getName();
            FieldComment fieldCommentAnnotation = field.getAnnotation(FieldComment.class);
            if (fieldCommentAnnotation != null) {
                meta.getFieldCommentMap().put(fieldName, fieldCommentAnnotation.value());
            }
            FieldLength fieldLengthAnnotation = field.getAnnotation(FieldLength.class);
            if (fieldLengthAnnotation != null) {
                meta.getFieldLengthMap().put(fieldName, fieldLengthAnnotation.length());
                meta.getFieldScaleMap().put(fieldName, fieldLengthAnnotation.scale());
            } else {
                meta.getFieldLengthMap().put(fieldName, 0);
                meta.getFieldScaleMap().put(fieldName, 0);
            }
            meta.getFieldClassMap().put(fieldName, field.getType());
        });
        DataBean data = tableXml.getData();
        return tableXml;
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
