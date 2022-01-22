package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.impl;

import gitee.com.ericfox.ddd.infrastructure.general.config.service.RabbitMqConfig;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqProxy;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqServerStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("rabbitMqServerStrategy")
@ConditionalOnBean(value = RabbitMqConfig.class)
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