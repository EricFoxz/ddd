package gitee.com.ericfox.ddd.starter.bpm.service;

import gitee.com.ericfox.ddd.starter.bpm.interfaces.BpmStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "custom.starter.bpm", value = "enable")
public class BpmService implements BpmStrategy {
    private final Map<String, BpmStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private BpmService(Map<String, BpmStrategy> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }


}
