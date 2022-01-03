package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ReflectUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;

import java.io.Serializable;

public interface LuceneBaseEntity<T extends BasePo<T>> {
    Serializable getId();
    T parent();

    default BasePo<T> toParent() {
        T t = parent();
        BeanUtil.copyProperties(this, t, false);
        return t;
    }
}
