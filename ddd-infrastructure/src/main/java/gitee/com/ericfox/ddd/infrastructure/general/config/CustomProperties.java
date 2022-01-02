package gitee.com.ericfox.ddd.infrastructure.general.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom", ignoreInvalidFields = true)
public class CustomProperties {
    private ResponseBean response;
    private String[] staticSources;

    @Getter
    @Setter
    public static class ResponseBean {
        private String keyOfErrorCode;
        private String keyOfErrorMessage;
    }
}
