package gitee.com.ericfox.ddd.common.interfaces.domain;

import java.io.Serializable;

/**
 * 上下文
 * 什么类型的(Description) + 谁/什么地点/什么东西(PartPlaceThing) + 以什么角色(Rule) + 在什么时间(Moment) + 做什么(Interaction)
 */
public interface BaseContext extends Serializable {
    interface BaseDescription {
    }

    interface BasePartPlaceThing {
    }

    interface BaseRule {
    }

    interface BaseMoment {
    }

    interface Interaction {
    }
}
