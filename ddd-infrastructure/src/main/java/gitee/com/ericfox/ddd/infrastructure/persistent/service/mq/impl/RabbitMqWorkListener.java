package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.impl;

import com.rabbitmq.client.Channel;
import gitee.com.ericfox.ddd.infrastructure.general.config.service.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RabbitMqWorkListener {
    @RabbitListener(queues = RabbitMqConfig.RABBIT_WORK_1)
    public void receiveMessage1(String msg, Channel channel, Message message) {
        log.warn("接收器1：" + msg);
    }

    @RabbitListener(queues = RabbitMqConfig.RABBIT_WORK_1)
    public void receiveMessage2(Object msg, Channel channel, Message message) {
        log.warn("接收器2：" + msg);
    }
}
