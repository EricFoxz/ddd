package gitee.com.ericfox.ddd.starter.cache.service;

import com.github.benmanes.caffeine.cache.Cache;
import gitee.com.ericfox.ddd.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.common.interfaces.starter.CacheService;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.starter.cache.config.CaffeineCacheConfig;
import gitee.com.ericfox.ddd.starter.cache.properties.StarterCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Caffeine缓存策略实现
 */
@Component
@Slf4j
@ConditionalOnBean(value = CaffeineCacheConfig.class)
public class CaffeineCacheStrategy implements CacheService {
    @Resource
    private CaffeineCache caffeineCache;
    @Resource
    private CacheManager cacheManager;
    @Resource
    private StarterCacheProperties starterCacheProperties;
    private CacheService l2Cache = null;

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
    public Boolean remove(Object key) {
        return caffeineCache.evictIfPresent(key);
    }

    @Override
    public Long removeByPrefix(String prefix) {
        int i = StrUtil.lastIndexOfIgnoreCase(prefix, ":");
        if (i >= 0) {
            String module = prefix.substring(0, i);
            String keyPrefix = prefix.substring(i + 1);
            return removeByPrefix(module, keyPrefix);
        }
        throw new ProjectFrameworkException("没有找到模块名称或前缀为空");
    }

    public Long removeByPrefix(String module, String prefix) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        int i = 0;
        String finalPrefix = prefix;
        Cache<?, ?> cache = (Cache<?, ?>) Objects.requireNonNull(cacheManager.getCache(module)).getNativeCache();
        cache.asMap().keySet().forEach(key -> {
            if (cn.hutool.core.util.StrUtil.startWith((String) key, finalPrefix)) {
                cache.invalidate(key);
                atomicInteger.incrementAndGet();
            }
        });
        return atomicInteger.longValue();
    }

    private Function<String, Object> getValueFromL2Cache(Object key) {
        return (t) -> getL2Cache() == null ? null : getL2Cache().get(key);
    }

    private CacheService getL2Cache() {
        if (l2Cache == null && ArrayUtil.length(starterCacheProperties.getDefaultStrategy()) >= 2) {
            l2Cache = SpringUtil.getBean(StrUtil.toCamelCase(starterCacheProperties.getDefaultStrategy()[1].getName()));
        }
        return l2Cache;
    }
}
