package gitee.com.ericfox.ddd.starter.config;

import gitee.com.ericfox.ddd.starter.bpm.properties.StarterBpmProperties;
import gitee.com.ericfox.ddd.starter.cache.properties.StarterCacheProperties;
import gitee.com.ericfox.ddd.starter.cloud.properties.StarterCloudProperties;
import gitee.com.ericfox.ddd.starter.mq.properties.StarterMqProperties;
import gitee.com.ericfox.ddd.starter.oauth2.properties.StarterOauth2Properties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "custom.starter", ignoreInvalidFields = true)
public class StarterProperties {
    private StarterBpmProperties bpm;

    private StarterCacheProperties cache;

    private StarterCloudProperties cloud;

    private StarterMqProperties mq;

    private StarterOauth2Properties oauth2;
}