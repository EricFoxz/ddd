package gitee.com.ericfox.ddd.starter.cloud.service;

import gitee.com.ericfox.ddd.common.interfaces.starter.CloudRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ConditionalOnProperty(prefix = "custom.starter.cloud", value = "enable")
public class CloudRegisterServiceImpl implements CloudRegisterService {
    private final Map<String, CloudRegisterService> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private CloudRegisterServiceImpl(Map<String, CloudRegisterService> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }
}
