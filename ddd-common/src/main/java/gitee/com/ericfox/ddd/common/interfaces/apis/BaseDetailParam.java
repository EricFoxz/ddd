package gitee.com.ericfox.ddd.common.interfaces.apis;

import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseEntity;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BasePo;

public interface BaseDetailParam<
        PO extends BasePo<PO>,
        ENTITY extends BaseEntity<PO, ENTITY>
        > {
    ENTITY toEntity();

    <DESCRIPTION extends BaseContext.BaseDescription> void set_description(DESCRIPTION _description);

    <MOMENT extends BaseContext.BaseMoment> void set_moment(MOMENT _moment);

    <RULE extends BaseContext.BaseRule> void set_rule(RULE _rule);
}
