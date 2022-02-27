package gitee.com.ericfox.ddd.starter.oauth2.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "custom.starter.oauth2")
public class StarterOauth2Properties {
    private boolean enable = true;
}
