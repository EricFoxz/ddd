package gitee.com.ericfox.ddd.infrastructure.general.config.env;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "custom.api", ignoreInvalidFields = true)
public class ApiProperties {
    private ResponseBean response;

    private String[] staticSources;

    @Setter
    @Getter
    public static class ResponseBean {
        private String keyOfErrorCode;
        private String keyOfErrorMessage;
        private String keyOfData;
    }
}
