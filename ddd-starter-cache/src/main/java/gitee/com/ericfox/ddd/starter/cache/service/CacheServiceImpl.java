package gitee.com.ericfox.ddd.starter.cache.service;

import gitee.com.ericfox.ddd.common.enums.strategy.CacheTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.interfaces.starter.CacheService;
import gitee.com.ericfox.ddd.common.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.starter.cache.properties.StarterCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存服务service
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "custom.starter.cache", value = "enable")
public class CacheServiceImpl implements CacheService {
    @Resource
    private StarterCacheProperties starterCacheProperties;

    private final List<CacheTypeStrategyEnum> strategyEnumList = CollUtil.newArrayList();

    private final Map<String, CacheService> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private CacheServiceImpl(Map<String, CacheService> strategyMap) {
        this.strategyMap.putAll(strategyMap);
    }

    public void put(Object key, Object value) {
        strategyMap.get(getBeanName()).put(key, value);
    }

    public Object get(Object key) {
        return strategyMap.get(getBeanName()).get(key);
    }

    @Override
    public Boolean delete(Object key) {
        return strategyMap.get(getBeanName()).delete(key);
    }

    @Override
    public Long flushByPrefix(String prefix) {
        return strategyMap.get(getBeanName()).flushByPrefix(prefix);
    }

    private String getBeanName() {
        if (CollUtil.isEmpty(this.strategyEnumList)) {
            for (StarterCacheProperties.CachePropertiesEnum defaultStrategy : starterCacheProperties.getDefaultStrategy()) {
                this.strategyEnumList.add(defaultStrategy.toBizEnum());
            }
        }
        return this.strategyEnumList.get(0).getCode();
    }
}
