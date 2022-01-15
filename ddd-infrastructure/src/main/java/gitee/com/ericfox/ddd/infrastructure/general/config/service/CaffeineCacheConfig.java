package gitee.com.ericfox.ddd.infrastructure.general.config.service;

import com.github.benmanes.caffeine.cache.*;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.framework.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy.CacheTypeStrategyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.*;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.CacheStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import java.time.Duration;

@Configuration
@ConditionalOnPropertyEnum(value = "custom.service.cache-strategy.default-strategy",
        enumClass = ServiceProperties.CacheStrategyBean.CachePropertiesEnum.class,
        includeAnyValue = "caffeine_cache_strategy")
@ConditionalOnProperty(prefix = "custom.service.cache-strategy", value = "enable")
@EnableCaching
@Slf4j
public class CaffeineCacheConfig {
    @Resource
    private ServiceProperties serviceProperties;
    private CacheStrategy l2Cache = null;

    @Bean
    public CaffeineCache caffeineCache() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(Duration.ofSeconds(7200))
                .maximumSize(1000);
        if (ArrayUtil.length(serviceProperties.getCacheStrategy().getDefaultStrategy()) >= 2) { //有2级缓存

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
        if (l2Cache == null && ArrayUtil.length(serviceProperties.getCacheStrategy().getDefaultStrategy()) >= 2) {
            l2Cache = SpringUtil.getBean(StrUtil.toCamelCase(serviceProperties.getCacheStrategy().getDefaultStrategy()[1].getName()));
        }
        return l2Cache;
    }
}
