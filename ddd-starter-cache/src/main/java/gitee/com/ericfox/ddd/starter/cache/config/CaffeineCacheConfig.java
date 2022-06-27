package gitee.com.ericfox.ddd.starter.cache.config;

import com.github.benmanes.caffeine.cache.*;
import gitee.com.ericfox.ddd.common.annotations.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.common.interfaces.starter.CacheService;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.starter.cache.properties.StarterCacheProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Caffeine缓存服务配置类
 */
@Configuration
@ConditionalOnPropertyEnum(
        name = "custom.starter.cache.default-strategy",
        enumClass = StarterCacheProperties.CachePropertiesEnum.class,
        includeAnyValue = "caffeine_cache_strategy"
)
@ConditionalOnProperty(prefix = "custom.starter.cache", value = "enable")
@EnableCaching
@Slf4j
public class CaffeineCacheConfig {
    @Resource
    private StarterCacheProperties starterCacheProperties;
    private CacheService l2Cache = null;
    private Caffeine<Object, Object> caffeine = null;

    @Autowired
    public synchronized Caffeine<Object, Object> getCaffeine() {
        if(caffeine == null) {
            caffeine = Caffeine.newBuilder()
                    .recordStats()
                    .expireAfterWrite(Duration.ofSeconds(7200))
                    .maximumSize(1000);
            if (ArrayUtil.length(starterCacheProperties.getDefaultStrategy()) >= 2) { //有2级缓存
                caffeine
                        .writer(new CacheWriter<Object, Object>() {
                            @Override
                            public void write(@NonNull Object key, @NonNull Object value) {
                                getL2Cache().put(key, value);
                            }

                            @Override
                            public void delete(@NonNull Object key, @Nullable Object value, @NonNull RemovalCause cause) {
                                getL2Cache().remove(key);
                            }
                        })
                        .build(new CacheLoader<Object, Object>() {
                            @Override
                            @SneakyThrows
                            public @Nullable Object load(@NonNull Object key) {
                                return l2Cache.get(key);
                            }
                        });
            }
        }
        return caffeine;
    }

    @Bean
    public CaffeineCache caffeineCache() {
        return new CaffeineCache("default", getCaffeine().build());
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeineSpec(CaffeineSpec.parse(starterCacheProperties.getCaffeineSpec()));
        cacheManager.setCaffeine(getCaffeine());
        return cacheManager;
    }

    private CacheService getL2Cache() {
        if (l2Cache == null && ArrayUtil.length(starterCacheProperties.getDefaultStrategy()) >= 2) {
            l2Cache = SpringUtil.getBean(StrUtil.toCamelCase(starterCacheProperties.getDefaultStrategy()[1].getName()));
        }
        return l2Cache;
    }
}
