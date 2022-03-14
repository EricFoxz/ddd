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
@ConditionalOnPropertyEnum(
        name = "custom.starter.mq.default-strategy",
        enumClass = StarterMqProperties.MqPropertiesEnum.class,
        includeAnyValue = "kafka_mq_strategy"
)
@ConditionalOnProperty(prefix = "custom.starter.mq", value = "enable")
@EnableKafka
public class KafkaMqConfig {
}
