package gitee.com.ericfox.ddd.starter.bpm.service;

import gitee.com.ericfox.ddd.common.interfaces.starter.BpmService;
import gitee.com.ericfox.ddd.starter.bpm.config.FlowableBpmConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(FlowableBpmConfig.class)
public class FlowableStrategy implements BpmService {
}
