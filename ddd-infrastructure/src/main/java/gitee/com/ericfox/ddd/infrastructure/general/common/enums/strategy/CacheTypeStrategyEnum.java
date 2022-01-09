package gitee.com.ericfox.ddd.infrastructure.general.common.enums.strategy;

import gitee.com.ericfox.ddd.infrastructure.general.common.enums.BaseEnum;
import org.springframework.stereotype.Component;

public enum CacheTypeStrategyEnum implements BaseEnum<CacheTypeStrategyEnum, String> {
    REDIS_STRATEGY("redisStrategy", "asd");

    private String code;
    private String description;

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
        return new CacheTypeStrategyEnum[0];
    }
}
