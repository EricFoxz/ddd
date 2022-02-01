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
    private final MetaBean meta = new MetaBean();
    private final DataBean data = new DataBean();

    @Data
    public static class MetaBean {
        private String domainName;
        private String tableName;
        private RepoTypeStrategyEnum repoTypeStrategyEnum;
        private String className;
        private String class_name;
        private String ClassName;
        private final Map<String, String> fields = MapUtil.newLinkedHashMap();
        private final List<String> indexList = CollUtil.newArrayList();

        private void setClass_name(String class_name) {
            setClassName(StrUtil.toCamelCase(class_name));
        }

        public void setClassName(String className) {
            this.className = StrUtil.toCamelCase(className);
            this.ClassName = StrUtil.upperFirst(this.className);
        }
    }

    @Data
    public static class DataBean {
        private Map<String, Serializable> dataMap = MapUtil.newLinkedHashMap();
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
