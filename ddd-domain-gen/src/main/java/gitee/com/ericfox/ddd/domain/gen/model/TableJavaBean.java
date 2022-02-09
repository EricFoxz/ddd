package gitee.com.ericfox.ddd.domain.gen.model;

import cn.hutool.core.bean.BeanDesc;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ClassUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.List;

@Getter
@Setter
public class TableJavaBean<PO extends BasePo<PO>> {
    private Class<PO> clazz;
    private final List<Field> fieldList = CollUtil.newArrayList();
    private final StructureBean structure = new StructureBean();

    @SneakyThrows
    public TableJavaBean(Class<PO> clazz) {
        this.clazz = clazz;
        BeanDesc beanDesc = BeanUtil.getBeanDesc(clazz);
        beanDesc.getProps().forEach(propDesc -> {
            fieldList.add(propDesc.getField());
        });
        Class<Object> innerClass = ClassUtil.loadClass(clazz.getName() + "$STRUCTURE");
        structure.domainName = (String) innerClass.getDeclaredField("domainName").get(null);
        structure.tableName = (String) innerClass.getDeclaredField("table").get(null);
        structure.id = (String) innerClass.getDeclaredField("id").get(null);
    }

    @Getter
    @Setter
    public static class StructureBean {
        private String domainName;
        private String tableName;
        private String id;
    }
}
