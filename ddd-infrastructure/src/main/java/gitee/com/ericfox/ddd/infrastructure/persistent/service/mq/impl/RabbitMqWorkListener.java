package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.impl;

import gitee.com.ericfox.ddd.infrastructure.general.config.service.RabbitMqConfig;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.mq.MqClientStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@ConditionalOnBean(value = RabbitMqConfig.class)
public class RabbitMqWorkListener implements MqClientStrategy {
    
}
