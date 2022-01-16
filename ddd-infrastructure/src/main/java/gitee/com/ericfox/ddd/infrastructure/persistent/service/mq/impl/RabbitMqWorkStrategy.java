package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.impl;

import gitee.com.ericfox.ddd.infrastructure.general.config.service.RabbitMqConfig;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("rabbitMqWorkerStrategy")
public class RabbitMqWorkStrategy implements MqStrategy {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RabbitMqConfig rabbitMqConfig;

    @Override
    public void send(String msg) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.RABBIT_WORK_1, msg);
    }
}