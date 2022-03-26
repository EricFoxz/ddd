package gitee.com.ericfox.ddd.starter.cloud.service;

import gitee.com.ericfox.ddd.starter.cloud.config.EurekaConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(EurekaConfig.class)
public class EurekaRegisterStrategy {
}
