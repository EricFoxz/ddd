package gitee.com.ericfox.ddd.starter.mq.config;

import gitee.com.ericfox.ddd.common.annotations.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.common.toolkit.coding.MapUtil;
import gitee.com.ericfox.ddd.starter.mq.properties.StarterMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import javax.annotation.Resource;
import java.util.Map;

/**
 * RabbitMq配置类
 */
@Configuration
@Slf4j
@ConditionalOnPropertyEnum(
        name = "custom.starter.mq.default-strategy",
        enumClass = StarterMqProperties.MqPropertiesEnum.class,
        includeAnyValue = "rabbit_mq_strategy"
)
@ConditionalOnProperty(prefix = "custom.service.mq-strategy", value = "enable")
@EnableRabbit
public class RabbitMqConfig {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public final Map<String, Queue> queueMap = MapUtil.newConcurrentHashMap();

    @Bean
    @ConditionalOnMissingBean(MessageHandlerMethodFactory.class)
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(RabbitListenerErrorHandler.class)
    public RabbitListenerErrorHandler rabbitListenerErrorHandler() {
        return (message, message1, e) -> {
            log.error("rabbitMqConfig::rabbitListenerErrorHandler " + e.getMessage() + "|" + e.getFailedMessage());
            throw new AmqpRejectAndDontRequeueException("reject");
        };
    }

    @Autowired
    public void config() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, s) -> {
            if (ack) {
                //消息消费成功
            } else {
                log.error("rabbitMqConfig::config 检测到ack标记false，消息发送失败");
            }
        });
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.error("rabbitMqConfig::config 消息发送失败：{}", returnedMessage);
        });
    }

    public synchronized Queue getQueue(String name) {
        if (queueMap.containsKey(name)) {
            return queueMap.get(name);
        }
        Queue queue = new Queue(name);
        queueMap.put(name, queue);
        return queue;
    }
}
