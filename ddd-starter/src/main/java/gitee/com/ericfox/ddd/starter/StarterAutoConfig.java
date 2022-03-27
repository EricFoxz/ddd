package gitee.com.ericfox.ddd.starter;

import gitee.com.ericfox.ddd.starter.config.StarterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(StarterProperties.class)
public class StarterAutoConfig {
}
