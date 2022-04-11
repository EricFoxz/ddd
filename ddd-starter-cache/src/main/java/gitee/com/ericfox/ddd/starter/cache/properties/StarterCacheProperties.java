package gitee.com.ericfox.ddd.starter.cache.properties;

import gitee.com.ericfox.ddd.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.CacheTypeStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.starter.cache")
public class StarterCacheProperties {
    private boolean enable = true;
    private CachePropertiesEnum[] defaultStrategy;
    private String caffeineSpec;
    private Integer defaultExpireSeconds = 3600;
    private boolean clearWhenStart = false;

    public enum CachePropertiesEnum implements BasePropertiesEnum<CacheTypeStrategyEnum> {
        CAFFEINE_CACHE_STRATEGY,
        REDIS_CACHE_STRATEGY;

        @Override
        public String getName() {
            return name();
        }

        @Override
        public CacheTypeStrategyEnum toBizEnum() {
            return CacheTypeStrategyEnum.CAFFEINE_CACHE_STRATEGY.getEnumByName(getName());
        }
    }
}
