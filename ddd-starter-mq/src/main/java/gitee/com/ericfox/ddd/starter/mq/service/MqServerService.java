package gitee.com.ericfox.ddd.starter.mq.service;

import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqProxy;
import gitee.com.ericfox.ddd.starter.mq.interfaces.MqServerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MQ服务端service
 */
@Service
@ConditionalOnProperty(prefix = "custom.service.mq-strategy", value = "enable")
public class MqServerService implements MqServerStrategy {
    private final Map<String, MqServerService> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private MqServerService(Map<String, MqServerService> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    @Override
    public void send(String msg, MqProxy mqProxy) {
        Class<? extends MqServerStrategy>[] types = mqProxy.getServerTypes();
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
