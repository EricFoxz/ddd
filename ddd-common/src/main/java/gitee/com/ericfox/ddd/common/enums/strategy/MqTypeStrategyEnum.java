package gitee.com.ericfox.ddd.common.enums.strategy;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;
import lombok.Getter;

/**
 * MQ策略枚举类
 */
@Getter
public enum MqTypeStrategyEnum implements BaseEnum<MqTypeStrategyEnum, String> {
    RABBIT_MQ_STRATEGY("rabbitMqStrategy", "rabbitMq"),
    KAFKA_MQ_STRATEGY("kafkaMqStrategy", "kafkaMq");

    private final String code;
    private final String comment;

    MqTypeStrategyEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public MqTypeStrategyEnum[] getEnums() {
        return values();
    }
}
