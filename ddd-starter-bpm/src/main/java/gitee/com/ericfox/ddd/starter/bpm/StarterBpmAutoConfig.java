package gitee.com.ericfox.ddd.starter.bpm;

import gitee.com.ericfox.ddd.starter.bpm.properties.StarterBpmProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableConfigurationProperties(StarterBpmProperties.class)
@ConditionalOnProperty(prefix = "custom.starter.bpm", value = "enable")
public class StarterBpmAutoConfig {
    @Resource
    private StarterBpmProperties starterMqProperties;

    public static void main(String[] args) {

    }
}
