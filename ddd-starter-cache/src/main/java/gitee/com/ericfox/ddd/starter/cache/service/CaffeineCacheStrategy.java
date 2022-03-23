package gitee.com.ericfox.ddd.starter.cache.service;

import gitee.com.ericfox.ddd.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.starter.cache.config.CaffeineCacheConfig;
import gitee.com.ericfox.ddd.starter.cache.interfaces.CacheStrategy;
import gitee.com.ericfox.ddd.starter.cache.properties.StarterCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Function;

/**
 * Caffeine缓存策略实现
 */
@Component
@Slf4j
@ConditionalOnBean(value = CaffeineCacheConfig.class)
public class CaffeineCacheStrategy implements CacheStrategy {
    @Resource
    private CaffeineCache caffeineCache;
    @Resource
    private StarterCacheProperties starterCacheProperties;
    private CacheStrategy l2Cache = null;

    @Override
    public void put(Object key, Object value) {
        caffeineCache.put(key, value);
    }

    @Override
    public Object get(Object key) {
        return caffeineCache.getNativeCache().get(key, t -> getValueFromL2Cache(key).apply((String) key));
//        return caffeineCache.get(key);
    }

    @Override
    public Boolean delete(Object key) {
        return caffeineCache.evictIfPresent(key);
    }

    @Override
    public Long flushByPrefix(String prefix) {
        log.error("caffeineCacheStrategy::flushByPrefix 暂未实现该方法");
        throw new ProjectFrameworkException("待实现");
    }

    private Function<String, Object> getValueFromL2Cache(Object key) {
        return (t) -> getL2Cache() == null ? null : getL2Cache().get(key);
    }

    private CacheStrategy getL2Cache() {
        if (l2Cache == null && ArrayUtil.length(starterCacheProperties.getDefaultStrategy()) >= 2) {
            l2Cache = SpringUtil.getBean(StrUtil.toCamelCase(starterCacheProperties.getDefaultStrategy()[1].getName()));
        }
        return l2Cache;
    }
}
