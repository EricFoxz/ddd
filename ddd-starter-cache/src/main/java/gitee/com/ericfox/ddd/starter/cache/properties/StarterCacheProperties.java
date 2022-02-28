package gitee.com.ericfox.ddd.starter.cache.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.starter.cache")
public class StarterCacheProperties {
    private boolean enable = true;
}
