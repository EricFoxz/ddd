package gitee.com.ericfox.ddd.common.enums.strategy;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 工作流引擎类型枚举类
 */
@Getter
public enum BpmTypeStrategyEnum implements BaseEnum<BpmTypeStrategyEnum, String> {
    FLOWABLE_STRATEGY("flowableStrategy", ""),
    ACTIVITI_STRATEGY("activitiStrategy", "");
    private final String code;
    private final String comment;

    BpmTypeStrategyEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public String getName() {
        return name();
    }

    @Override
    public BpmTypeStrategyEnum[] getEnums() {
        return new BpmTypeStrategyEnum[0];
    }
}
