package gitee.com.ericfox.ddd.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "custom.api", ignoreInvalidFields = true)
public class ApiProperties {
    public ResponseBean response;

    private String[] staticSources;

    @Setter
    @Getter
    public static class ResponseBean {
        private String keyOfErrorCode;
        private String keyOfErrorMessage;
        private String keyOfData;
    }
}
