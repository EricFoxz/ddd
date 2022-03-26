package gitee.com.ericfox.ddd.starter.bpm.service;

import gitee.com.ericfox.ddd.common.interfaces.starter.BpmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "custom.starter.bpm", value = "enable")
public class BpmServiceImpl implements BpmService {
    private final Map<String, BpmService> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private BpmServiceImpl(Map<String, BpmService> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }
}
