package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import cn.hutool.core.collection.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ReflectUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public interface BasePo<T extends BasePo<T>> extends Serializable {
    Serializable getId();

    default List<String> fields() {
        Field[] fields = ReflectUtil.getFields(this.getClass());
        List<String> list = CollUtil.newArrayList();
        for (Field field : fields) {
            list.add(field.getName());
        }
        return list;
    }

    final class STRUCTURE {
        public static String table;
        public static String id;
    }
}
