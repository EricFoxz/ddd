package gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BaseEnum;

/**
 * MQ策略枚举类
 */
public enum MqTypeStrategyEnum implements BaseEnum<MqTypeStrategyEnum, String> {
    RABBIT_MQ_STRATEGY("rabbitMqStrategy", "rabbitMq"),
    KAFKA_MQ_STRATEGY("kafkaMqStrategy", "kafkaMqStrategy");

    private final String code;
    private final String description;

    MqTypeStrategyEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public MqTypeStrategyEnum[] getEnums() {
        return values();
    }
}
