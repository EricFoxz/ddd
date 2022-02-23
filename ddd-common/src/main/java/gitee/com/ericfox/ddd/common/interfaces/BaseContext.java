package gitee.com.ericfox.ddd.common.interfaces;

import java.io.Serializable;

/**
 * 上下文
 * 谁/什么地点/什么东西(PartPlaceThing) + 以什么角色（Rule） + 在什么时间（Moment） +  + 做什么（Description）
 */
public interface BaseContext extends Serializable {
    public static interface BasePartPlaceThing {
    }

    interface BaseRule {
    }

    interface BaseMoment {
    }

    interface BaseDescription {
    }
}
