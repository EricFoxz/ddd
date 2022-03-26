package gitee.com.ericfox.ddd.starter.cloud.service;

import gitee.com.ericfox.ddd.common.interfaces.starter.CloudRegisterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "custom.starter.cloud", value = "enable")
public class CloudRegisterServiceImpl implements CloudRegisterService {
}
