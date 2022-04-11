package gitee.com.ericfox.ddd.starter.mq.properties;

import gitee.com.ericfox.ddd.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.MqTypeStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.starter.mq")
public class StarterMqProperties {

    private boolean enable = true;

    private boolean enableServerMode = true;

    private boolean enableClientMode = false;

    private MqPropertiesEnum[] defaultStrategy;

    public enum MqPropertiesEnum implements BasePropertiesEnum<MqTypeStrategyEnum> {
        RABBIT_MQ_STRATEGY,
        KAFKA_MQ_STRATEGY;

        @Override
        public String getName() {
            return name();
        }

        @Override
        public MqTypeStrategyEnum toBizEnum() {
            return MqTypeStrategyEnum.RABBIT_MQ_STRATEGY.getEnumByName(getName());
        }
    }
}
