package gitee.com.ericfox.ddd.starter.cloud.config;

import gitee.com.ericfox.ddd.common.annotations.ConditionalOnPropertyEnum;
import gitee.com.ericfox.ddd.starter.cloud.properties.StarterCloudProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
//@EnableEurekaClient
@ConditionalOnProperty(prefix = "custom.starter.cloud", value = "enable")
@ConditionalOnPropertyEnum(
        name = "custom.starter.cloud.default-register-strategy",
        enumClass = StarterCloudProperties.CloudRegisterPropertiesEnum.class,
        includeAnyValue = "eureka_register_strategy"
)
public class EurekaConfig {
    @Resource
    private StarterCloudProperties starterCloudProperties;
}
