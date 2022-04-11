package gitee.com.ericfox.ddd.common.enums.strategy;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 缓存策略枚举类
 */
@Getter
public enum CloudRegisterTypeStrategyEnum implements BaseEnum<CloudRegisterTypeStrategyEnum, String> {
    EUREKA_REGISTER_STRATEGY("eurekaRegisterStrategy", "eureka同步服务"),
    ZOOKEEPER_REGISTER_STRATEGY("zookeeperRegisterStrategy", "zookeeper同步服务");

    private final String code;
    private final String comment;

    CloudRegisterTypeStrategyEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public CloudRegisterTypeStrategyEnum[] getEnums() {
        return values();
    }
}
