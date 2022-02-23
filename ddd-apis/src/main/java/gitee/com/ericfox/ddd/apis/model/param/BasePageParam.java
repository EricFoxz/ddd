package gitee.com.ericfox.ddd.apis.model.param;

import gitee.com.ericfox.ddd.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.BasePo;

public interface BasePageParam<PO extends BasePo<PO>, ENTITY extends BaseEntity<PO, ENTITY>> {
    ENTITY toEntity();
}
