package gitee.com.ericfox.ddd.starter.mq.config;

import gitee.com.ericfox.ddd.common.annotations.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.starter.mq.properties.StarterMqProperties;
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
        enumClass = StarterMqProperties.MqPropertiesEnum.class,
        includeAnyValue = "kafka_mq_strategy"
)
@ConditionalOnProperty(prefix = "custom.service.mq-strategy", value = "enable")
public class KafkaMqConfig {
}
