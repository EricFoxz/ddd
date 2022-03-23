package gitee.com.ericfox.ddd.starter.cache.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.CacheWriter;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import gitee.com.ericfox.ddd.common.annotations.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.common.toolkit.coding.ArrayUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.starter.cache.interfaces.CacheStrategy;
import gitee.com.ericfox.ddd.starter.cache.properties.StarterCacheProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.Duration;

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
    private CacheStrategy l2Cache = null;

    @Bean
    public CaffeineCache caffeineCache() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
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
                            getL2Cache().delete(key);
                        }
                    })
                    .build(new CacheLoader<Object, Object>() {
                        @Override
                        @SneakyThrows
                        public @Nullable Object load(@NonNull Object key) {
                            return l2Cache.get(key);
                        }
                    });
            new CaffeineCache("default", caffeine.build());
        }
        return new CaffeineCache("default", caffeine.build());
    }

    private CacheStrategy getL2Cache() {
        if (l2Cache == null && ArrayUtil.length(starterCacheProperties.getDefaultStrategy()) >= 2) {
            l2Cache = SpringUtil.getBean(StrUtil.toCamelCase(starterCacheProperties.getDefaultStrategy()[1].getName()));
        }
        return l2Cache;
    }
}
