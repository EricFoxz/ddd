package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConditionalOnProperty(prefix = "custom.service.mq-strategy", value = "enable")
public class MqClientService implements MqClientStrategy {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;
    private final Map<String, MqClientService> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private MqClientService(Map<String, MqClientService> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }
}
