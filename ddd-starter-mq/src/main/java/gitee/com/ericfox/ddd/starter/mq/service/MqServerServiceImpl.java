package gitee.com.ericfox.ddd.starter.mq.service;

import gitee.com.ericfox.ddd.common.interfaces.starter.MqProxy;
import gitee.com.ericfox.ddd.common.interfaces.starter.MqServerService;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MQ服务端service
 */
@Service
@ConditionalOnProperty(prefix = "custom.starter.mq", value = "enable")
public class MqServerServiceImpl implements MqServerService {
    private final Map<String, MqServerServiceImpl> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private MqServerServiceImpl(Map<String, MqServerServiceImpl> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    @Override
    public void send(String msg, MqProxy mqProxy) {
        Class<? extends MqServerService>[] types = mqProxy.getServerTypes();
        if (ArrayUtil.isNotEmpty(types)) {
            for (Class<? extends MqServerService> type : types) {
                for (MqServerServiceImpl value : strategyMap.values()) {
                    if (value.getClass().equals(type)) {
                        mqProxy.getQueueNames();
                        value.send(msg, mqProxy);
                    }
                }
            }
        }
    }
}
