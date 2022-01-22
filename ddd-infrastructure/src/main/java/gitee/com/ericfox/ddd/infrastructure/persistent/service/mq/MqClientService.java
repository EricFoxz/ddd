package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ConditionalOnProperty(prefix = "custom.service.mq-strategy", value = "enable")
public class MqClientService implements MqClientStrategy {
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;
    private final Map<String, MqClientService> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private MqClientService(Map<String, MqClientService> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    @Override
    public void addListener(MqProxy mqProxy) {
        Class<? extends MqClientStrategy>[] types = mqProxy.getClientTypes();
        if (ArrayUtil.isNotEmpty(types)) {
            for (Class<? extends MqClientStrategy> type : types) {
                for (MqClientService value : strategyMap.values()) {
                    if (value.getClass().equals(type)) {
                        value.addListener(mqProxy);
                    }
                }
            }
        }
    }
}
