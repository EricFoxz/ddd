package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ReflectUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;

import java.io.Serializable;

public interface BaseDao<PO extends BasePo<PO>> {
    String TO_PO_METHOD_NAME = "toPo";

    Serializable getId();

    Class<PO> poClass();

    default String primaryKeyFieldName() {
        return "id";
    }

    /**
     * 由Dao转换为实体
     */
    default BasePo<PO> toPo() {
        Class<PO> clazz = poClass();
        PO po = ReflectUtil.newInstance(clazz);
        BeanUtil.copyProperties(this, po, false);
        return po;
    }
}
