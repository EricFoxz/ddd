package gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BaseEnum;

/**
 * 缓存策略枚举类
 */
public enum CacheTypeStrategyEnum implements BaseEnum<CacheTypeStrategyEnum, String> {
    REDIS_CACHE_STRATEGY("redisCacheStrategy", "redis缓存"),
    CAFFEINE_CACHE_STRATEGY("caffeineCacheStrategy", "caffeine缓存");

    private final String code;
    private final String description;

    CacheTypeStrategyEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public CacheTypeStrategyEnum[] getEnums() {
        return values();
    }
}
