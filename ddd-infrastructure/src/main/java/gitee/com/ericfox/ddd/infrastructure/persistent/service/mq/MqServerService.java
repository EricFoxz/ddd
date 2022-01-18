package gitee.com.ericfox.ddd.infrastructure.persistent.service.mq;

import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ConditionalOnProperty(prefix = "custom.service.mq-strategy", value = "enable")
public class MqServerService implements MqServerStrategy {
    private final Map<String, MqServerService> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private MqServerService(Map<String, MqServerService> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    @Override
    public void send(String msg, MqProxy mqProxy) {
        Class<? extends MqServerStrategy>[] types = mqProxy.getTypes();
        if (ArrayUtil.isNotEmpty(types)) {
            for (Class<? extends MqServerStrategy> type : types) {
                for (MqServerService value : strategyMap.values()) {
                    if (value.getClass().equals(type)) {
                        mqProxy.getQueueNames();
                        value.send(msg, mqProxy);
                    }
                }
            }
        }
    }
}
