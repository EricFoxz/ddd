package gitee.com.ericfox.ddd.common.enums.strategy;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;

/**
 * 工作流引擎类型枚举类
 */
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
    public String getCode() {
        return code;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public BpmTypeStrategyEnum[] getEnums() {
        return new BpmTypeStrategyEnum[0];
    }
}
