package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.impl;

import gitee.com.ericfox.ddd.infrastructure.general.config.service.RabbitMqConfig;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqClientStrategy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.MethodRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RabbitMQ策略 客户端实现类
 */
@Slf4j
@Component("rabbitMqClientStrategy")
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

    @Autowired
    public void test() {
        System.out.println();
    }

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
