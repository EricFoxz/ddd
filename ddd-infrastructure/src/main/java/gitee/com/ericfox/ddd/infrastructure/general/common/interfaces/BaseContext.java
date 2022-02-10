package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import java.io.Serializable;

/**
 * 上下文
 * 谁/什么地点/什么东西(PartPlaceThing) + 以什么角色（Rule） + 在什么时间（Moment） +  + 做什么（Description）
 */
public interface BaseContext<ENTITY extends BaseContext.BasePartPlaceThing, RULE extends BaseContext.BaseRule, MOMENT extends BaseContext.BaseMoment, DESC extends BaseContext.BaseDescription> extends Runnable, Serializable {
    void setPartPlaceThing(ENTITY partPlaceThing);

    ENTITY getPartPlaceThing();

    void setRule(RULE rule);

    RULE getRule();

    void setMoment(MOMENT moment);

    MOMENT getMoment();

    void setDescription(DESC description);

    DESC getDescription();

    interface BaseRule {
    }

    interface BasePartPlaceThing {
    }

    interface BaseMoment {
    }

    interface BaseDescription {
        String getComment();
    }
}
