package gitee.com.ericfox.ddd.infrastructure.general.config.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootConfiguration
@Slf4j
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
