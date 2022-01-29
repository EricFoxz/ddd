package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "SysUserService", keyGenerator = Constants.KEY_GENERATOR)
public class SysUserService extends SysUserServiceBase {
}
