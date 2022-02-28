package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "ServiceCache:SysUserService", keyGenerator = Constants.SERVICE_CACHE_KEY_GENERATOR)
public class SysUserService extends SysUserServiceBase {
    /**
     * 用户注册
     */
    @Transactional
    public boolean register(SysUserEntity entity) {
        SysUserContext.Rule rule = entity.get_rule();
        SysUserContext.Moment moment = entity.get_moment();
        if (rule.equals(SysUserContext.Rule.MANAGER)) {
            insert(entity);
        }
        return true;
    }
}
