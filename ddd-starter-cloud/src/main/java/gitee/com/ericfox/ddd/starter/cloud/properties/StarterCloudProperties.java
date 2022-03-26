package gitee.com.ericfox.ddd.starter.cloud.properties;

import gitee.com.ericfox.ddd.common.enums.BasePropertiesEnum;
import gitee.com.ericfox.ddd.common.enums.strategy.CloudRegisterTypeStrategyEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;

import javax.annotation.Resource;

@Getter
@Setter
@ConfigurationProperties(prefix = "custom.starter.cloud")
public class StarterCloudProperties {
    @Resource
    private EurekaClientConfigBean eurekaClientConfigBean;

    private boolean enable = false;
    private CloudRegisterPropertiesEnum defaultRegisterStrategy;

    public enum CloudRegisterPropertiesEnum implements BasePropertiesEnum<CloudRegisterTypeStrategyEnum> {
        EUREKA_REGISTER_STRATEGY;

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public CloudRegisterTypeStrategyEnum toBizEnum() {
            return CloudRegisterTypeStrategyEnum.EUREKA_REGISTER_STRATEGY.getEnumByName(this.name());
        }
    }

    private void setEurekaClientConfigBean(EurekaClientConfigBean eurekaClientConfigBean) {
    }
}
