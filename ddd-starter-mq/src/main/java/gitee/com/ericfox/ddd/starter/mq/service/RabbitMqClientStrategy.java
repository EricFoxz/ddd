package gitee.com.ericfox.ddd.starter.mq.service;

import gitee.com.ericfox.ddd.starter.mq.config.RabbitMqConfig;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqClientStrategy;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RabbitMQ策略 客户端实现类
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "custom.starter.mq", value = "enable")
@ConditionalOnBean(RabbitMqConfig.class)
public class RabbitMqClientStrategy implements MqClientStrategy {
    @Resource
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    @Resource
    private RabbitListenerContainerFactory rabbitListenerContainerFactory;
    @Resource
    private MessageHandlerMethodFactory messageHandlerMethodFactory;
    @Resource
    private RabbitListenerErrorHandler rabbitListenerErrorHandler;

    @Override
    public void addListener(MqProxy mqProxy) {
        for (String queueName : mqProxy.getQueueNames()) {
            MethodRabbitListenerEndpoint endpoint = new MethodRabbitListenerEndpoint();
            endpoint.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
            endpoint.setId(queueName);
            endpoint.setQueueNames(queueName);
            endpoint.setErrorHandler(rabbitListenerErrorHandler);
            rabbitListenerEndpointRegistry.registerListenerContainer(endpoint, rabbitListenerContainerFactory);
        }
    }
}
