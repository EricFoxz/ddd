package gitee.com.ericfox.ddd.infrastructure.general.config.service;

import gitee.com.ericfox.ddd.infrastructure.general.common.annos.framework.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@Slf4j
@ConditionalOnPropertyEnum(
        value = "custom.service.mq-strategy.default-strategy",
        enumClass = ServiceProperties.MqStrategyBean.MqPropertiesEnum.class,
        includeAnyValue = "kafka_mq_strategy"
)
@ConditionalOnProperty(prefix = "custom.service.mq-strategy", value = "enable")
@EnableRabbit
public class RabbitMqConfig {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public static final String RABBIT_WORK_1 = "rabbit_work1";

    @Bean
    public Queue queue() {
        return new Queue(RABBIT_WORK_1);
    }

    @Autowired
    public void config() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, s) -> {
            if (ack) {
                //消息消费成功
            } else {
                log.error("rabbitMqConfig检测到ack标记false，消息发送失败");
            }
        });
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.error("rabbitMqConfig消息发送失败：{}", returnedMessage);
        });
    }
}
