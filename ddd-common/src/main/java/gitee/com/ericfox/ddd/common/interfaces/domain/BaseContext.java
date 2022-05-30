package gitee.com.ericfox.ddd.common.interfaces.domain;

import gitee.com.ericfox.ddd.common.interfaces.infrastructure.Constants;
import gitee.com.ericfox.ddd.common.toolkit.coding.BeanUtil;

import java.io.Serializable;

/**
 * 上下文
 * 什么类型的(Description) + 谁/什么地点/什么东西(PartPlaceThing) + 以什么角色(Rule) + 在什么时间(Moment) + 做什么(Interaction)
 */
public interface BaseContext extends Serializable {
    /**
     * 类型
     */
    interface BaseDescription {
        String TYPE = "DESCRIPTION" ;

        default void load(Object o) {
            BeanUtil.copyProperties(o, this, Constants.CAMEL_CASE_KEY_COPY_OPTIONS);
        }
    }

    /**
     * 主体（一般指代Entity）
     */
    interface BasePartPlaceThing {
        String TYPE = "PART_PLACE_THING" ;
    }

    /**
     * 角色
     */
    interface BaseRule {
        String TYPE = "RULE" ;

        default void load(Object o) {
            BeanUtil.copyProperties(o, this, Constants.CAMEL_CASE_KEY_COPY_OPTIONS);
        }
    }

    /**
     * 时间
     */
    interface BaseMoment {
        String TYPE = "MOMENT" ;

        String getResponseBodyScript();

        default void load(Object o) {
            BeanUtil.copyProperties(o, this, Constants.CAMEL_CASE_KEY_COPY_OPTIONS);
        }
    }

    /**
     * 做什么（由service实现）
     */
    interface BaseInteraction {
        String TYPE = "INTERACTION" ;
    }
}
