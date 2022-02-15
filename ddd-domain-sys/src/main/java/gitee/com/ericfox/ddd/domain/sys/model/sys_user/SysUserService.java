package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "SysUserService", keyGenerator = Constants.SERVICE_CACHE_KEY_GENERATOR)
public class SysUserService extends SysUserServiceBase {
}
