package gitee.com.ericfox.ddd.apis.model.param;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.BasePo;

public interface BasePageParam<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>> {
    ENTITY toEntity();
}
