package gitee.com.ericfox.ddd.infrastructure.general.common.interfaces;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 上下文
 * 谁/什么地点/什么东西(PartPlaceThing) + 以什么角色（Rule） + 在什么时间（Moment） +  + 做什么（Description）
 */
public interface BaseContext extends Serializable {
    interface BasePartPlaceThing {
    }

    interface BaseRule {
    }

    interface BaseMoment {
    }

    interface BaseDescription extends Function {
    }
}
