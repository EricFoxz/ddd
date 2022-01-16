package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.impl.RabbitMqWorkStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MqService implements MqStrategy {
    @Resource
    private RabbitMqWorkStrategy rabbitMqWorkerStrategy;

    @Override
    public void send(String msg) {
        rabbitMqWorkerStrategy.send(msg);
    }
}
