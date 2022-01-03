package gitee.com.ericfox.ddd.infrastructure.general.config.env;

import gitee.com.ericfox.ddd.infrastructure.general.common.constants.ActiveProperties;
import gitee.com.ericfox.ddd.infrastructure.general.common.enums.CacheStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom", ignoreInvalidFields = true)
public class CustomProperties {
    public CustomProperties() {
        ActiveProperties.customProperties = this;
    }

    private ResponseBean response;
    private String redisPoPrefix;
    private String cacheStrategy;
    private String[] staticSources;

    @Getter
    @Setter
    public static class ResponseBean {
        private String keyOfErrorCode;
        private String keyOfErrorMessage;
    }
}
