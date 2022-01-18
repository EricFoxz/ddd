package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.impl;

import gitee.com.ericfox.ddd.infrastructure.general.config.service.RabbitMqConfig;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqClientStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service("rabbitMqListenerStrategy")
@ConditionalOnBean(value = RabbitMqConfig.class)
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
