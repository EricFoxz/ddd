package gitee.com.ericfox.ddd.apis.model.param;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;

public interface BaseDetailParam<T extends BasePo<T>, V extends BaseEntity<T, V>> {
    V toEntity();
}
