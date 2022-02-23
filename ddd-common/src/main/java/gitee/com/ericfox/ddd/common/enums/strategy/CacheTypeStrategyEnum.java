package gitee.com.ericfox.ddd.common.enums.strategy;

import gitee.com.ericfox.ddd.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 缓存策略枚举类
 */
@Getter
public enum CacheTypeStrategyEnum implements BaseEnum<CacheTypeStrategyEnum, String> {
    REDIS_CACHE_STRATEGY("redisCacheStrategy", "redis缓存"),
    CAFFEINE_CACHE_STRATEGY("caffeineCacheStrategy", "caffeine缓存");

    private final String code;
    private final String comment;

    CacheTypeStrategyEnum(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public CacheTypeStrategyEnum[] getEnums() {
        return values();
    }
}
