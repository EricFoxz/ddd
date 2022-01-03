package gitee.com.ericfox.ddd.infrastructure.general.common.enums;

public enum  CacheStrategyEnum implements BaseEnum<CacheStrategyEnum, String> {
    REDIS_STRATEGY("redisCacheStrategy", "redis缓存方式");

    private final String code;
    private final String description;
    CacheStrategyEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public CacheStrategyEnum[] getEnums() {
        return new CacheStrategyEnum[0];
    }
}
