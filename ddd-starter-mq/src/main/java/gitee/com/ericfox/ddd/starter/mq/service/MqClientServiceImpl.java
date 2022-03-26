package gitee.com.ericfox.ddd.starter.mq.service;

import gitee.com.ericfox.ddd.common.interfaces.starter.MqClientService;
import gitee.com.ericfox.ddd.common.interfaces.starter.MqProxy;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MQ客户端service
 */
@Service
@ConditionalOnProperty(prefix = "custom.starter.mq", value = "enable")
public class MqClientServiceImpl implements MqClientService {
    private final Map<String, MqClientServiceImpl> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private MqClientServiceImpl(Map<String, MqClientServiceImpl> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    @Override
    public void addListener(MqProxy mqProxy) {
        Class<? extends MqClientService>[] types = mqProxy.getClientTypes();
        if (ArrayUtil.isNotEmpty(types)) {
            for (Class<? extends MqClientService> type : types) {
                for (MqClientServiceImpl value : strategyMap.values()) {
                    if (value.getClass().equals(type)) {
                        value.addListener(mqProxy);
                    }
                }
            }
        }
    }
}
