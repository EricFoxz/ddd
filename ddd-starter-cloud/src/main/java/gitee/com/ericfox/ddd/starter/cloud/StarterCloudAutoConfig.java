package gitee.com.ericfox.ddd.starter.cloud;

import gitee.com.ericfox.ddd.starter.cloud.properties.StarterCloudProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(StarterCloudProperties.class)
@ConditionalOnProperty(prefix = "custom.starter.cloud", value = "enable")
public class StarterCloudAutoConfig {
}
