package gitee.com.ericfox.ddd.infrastructure.general.config.service;

import gitee.com.ericfox.ddd.infrastructure.general.common.annotations.framework.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Kafka配置类
 */
@Configuration
@EnableKafka
@ConditionalOnPropertyEnum(
        value = "custom.service.mq-strategy.default-strategy",
        enumClass = ServiceProperties.MqStrategyBean.MqPropertiesEnum.class,
        includeAnyValue = "kafka_mq_strategy"
)
@ConditionalOnProperty(prefix = "custom.service.mq-strategy", value = "enable")
public class KafkaMqConfig {
}
