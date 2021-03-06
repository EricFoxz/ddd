package gitee.com.ericfox.ddd.infrastructure.general.config.env;

import gitee.com.ericfox.ddd.starter.config.StarterProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom", ignoreInvalidFields = true)
public class CustomProperties {
    private String projectName;
    private String rootPackage;
    private InfrastructureProperties infrastructure;
    private ApiProperties api;
    private StarterProperties starterProperties;
}
