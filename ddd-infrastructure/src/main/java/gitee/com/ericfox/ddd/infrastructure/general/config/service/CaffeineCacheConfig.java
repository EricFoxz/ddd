package gitee.com.ericfox.ddd.infrastructure.general.config.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.framework.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.infrastructure.general.config.env.ServiceProperties;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@Configuration
@ConditionalOnPropertyEnum(value = "custom.service.cache-strategy.default-strategy",
        enumClass = ServiceProperties.CacheStrategyBean.CachePropertiesEnum.class,
        includeAnyValue = "caffeine_strategy")
@ConditionalOnProperty(prefix = "custom.service.cache-strategy", value = "enable")
@EnableCaching
@Slf4j
public class CaffeineCacheConfig {
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        Cache<Object, Object> good = Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(Duration.ofSeconds(60))
                .maximumSize(1000)
                .build();
        Cache<Object, Object> homePage = Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(Duration.ofSeconds(7200))
                .maximumSize(1000)
                .build();
        cacheManager.setCaches(CollUtil.newArrayList(
                new CaffeineCache("good", good),
                new CaffeineCache("homePage", homePage)
        ));
        return cacheManager;
    }
}
