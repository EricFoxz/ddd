package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ReflectUtil;

import java.io.Serializable;

public interface BaseDao<T extends BasePo<T>> {
    String TO_PO_METHOD_NAME = "toPo";

    Serializable getId();

    Class<T> poClass();

    default String primaryKeyFieldName() {
        return "id";
    }

    /**
     * 由Dao转换为实体
     */
    default BasePo<T> toPo() {
        Class<T> clazz = poClass();
        T po = ReflectUtil.newInstance(clazz);
        BeanUtil.copyProperties(this, po, false);
        return po;
    }
}
