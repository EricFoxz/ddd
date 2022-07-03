package gitee.com.ericfox.ddd.common.interfaces.apis;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;

public interface BasePageParam<
        PO extends BasePo<PO>,
        ENTITY extends BaseEntity<PO, ENTITY>
        > {
    ENTITY toEntity();

    void set_description(BaseContext.BaseDescription _description);

    void set_moment(BaseContext.BaseMoment _moment);

    void set_rule(BaseContext.BaseRule _rule);
}
