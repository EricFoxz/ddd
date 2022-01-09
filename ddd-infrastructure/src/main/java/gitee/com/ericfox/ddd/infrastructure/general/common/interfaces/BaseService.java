package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;

public interface BaseService<T extends BasePo<T>, V extends BaseEntity<T, V>> {
    V insert(V entity);

    boolean deleteById(V entity);

    boolean update(V entity);
}
