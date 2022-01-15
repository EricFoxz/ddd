package gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.impl;

import gitee.com.ericfox.ddd.infrastructure.general.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import gitee.com.ericfox.ddd.infrastructure.general.config.service.CaffeineCacheConfig;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.CacheStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.function.Function;

@Service("caffeineCacheStrategy")
@Slf4j
@ConditionalOnBean(value = CaffeineCacheConfig.class)
public class CaffeineCacheStrategy implements CacheStrategy {
    @Resource
    private CaffeineCache caffeineCache;
    @Resource
    private ServiceProperties serviceProperties;
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
        log.error("caffeineCacheStrategy未实现该方法");
        throw new ProjectFrameworkException("待实现");
    }

    private Function<String, Object> getValueFromL2Cache(Object key) {
        return (t) -> getL2Cache() == null ? null : getL2Cache().get(key);
    }

    private CacheStrategy getL2Cache() {
        if (l2Cache == null && ArrayUtil.length(serviceProperties.getCacheStrategy().getDefaultStrategy()) >= 2) {
            l2Cache = SpringUtil.getBean(StrUtil.toCamelCase(serviceProperties.getCacheStrategy().getDefaultStrategy()[1].getName()));
        }
        return l2Cache;
    }
}
