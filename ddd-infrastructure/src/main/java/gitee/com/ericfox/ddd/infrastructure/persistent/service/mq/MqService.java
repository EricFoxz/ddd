package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MqService implements MqStrategy {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;
    private final Map<String, MqService> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private MqService(Map<String, MqService> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    @Override
    public void send(String msg) {
        strategyMap.get(0).send(msg);
    }

    public void sendKafka(String msg) {
        kafkaTemplate.send("topic1", msg);
    }
}
