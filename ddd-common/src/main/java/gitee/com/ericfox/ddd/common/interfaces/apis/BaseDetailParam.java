package gitee.com.ericfox.ddd.common.interfaces.apis;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;

public interface BaseDetailParam<
        PO extends BasePo<PO>,
        ENTITY extends BaseEntity<PO, ENTITY>,
        DESCRIPTION extends BaseContext.BaseDescription,
        MOMENT extends BaseContext.BaseMoment,
        RULE extends BaseContext.BaseRule
        > {
    ENTITY toEntity();

    void set_description(DESCRIPTION _description);

    void set_moment(MOMENT _moment);

    void set_rule(RULE _rule);
}
