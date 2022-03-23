package gitee.com.ericfox.ddd.starter.mq.service;

import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.starter.mq.config.RabbitMqConfig;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqProxy;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqServerStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RabbitMQ策略 服务端实现类
 */
@Component
@ConditionalOnProperty(prefix = "custom.starter.mq", value = "enable")
@ConditionalOnBean(RabbitMqConfig.class)
public class RabbitMqServerStrategy implements MqServerStrategy {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String msg, MqProxy mqProxy) {
        String[] names = mqProxy.getQueueNames();
        if (ArrayUtil.isNotEmpty(names)) {
            for (String name : names) {
                rabbitTemplate.convertAndSend(name, msg);
            }
        }
    }
}