package gitee.com.ericfox.ddd.infrastructure.persistent.service.cache;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.CacheTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CacheService implements CacheStrategy {
    @Resource
    private ServiceProperties serviceProperties;

    private CacheTypeStrategyEnum strategyEnum;

    private final Map<String, CacheStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public CacheService(Map<String, CacheStrategy> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    public void set(String key, Object value) {
        strategyMap.get(getBeanName()).set(key, value);
    }

    public Object get(String key) {
        return strategyMap.get(getBeanName()).get(key);
    }

    @Override
    public Long flushByPrefix(String prefix) {
        return strategyMap.get(getBeanName()).flushByPrefix(prefix);
    }

    private String getBeanName() {
        if (this.strategyEnum == null) {
            this.strategyEnum = serviceProperties.getCacheStrategy().getDefaultStrategy().toBizEnum();
        }
        return this.strategyEnum.getCode();
    }
}
