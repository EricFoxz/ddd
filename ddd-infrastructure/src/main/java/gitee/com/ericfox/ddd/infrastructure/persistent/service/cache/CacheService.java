package gitee.com.ericfox.ddd.infrastructure.persistent.service.cache;

import gitee.com.ericfox.ddd.infrastructure.general.common.constants.ActiveProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CacheService {
    private final String beanName = ActiveProperties.customProperties.getCacheStrategy();
    private final Map<String, CacheStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public CacheService(Map<String, CacheStrategy> strategyMap) {
        strategyMap.forEach(this.strategyMap::put);
    }

    public void set(String key, Object value) {
        strategyMap.get(beanName).set(key, value);
    }

    public Object get(String key) {
        return strategyMap.get(beanName).get(key);
    }
}
