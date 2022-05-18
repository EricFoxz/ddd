package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.common.toolkit.coding.SecureUtil;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenService;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@CacheConfig(cacheNames = "ServiceCache:SysUserService", keyGenerator = Constants.SERVICE_CACHE_KEY_GENERATOR)
public class SysUserService extends SysUserServiceBase {
    @Resource
    private SysTokenService sysTokenService;

    /**
     * 用户注册
     */
    @Transactional
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean register(SysUserEntity entity) {
        SysUserContext.Rule rule = entity.get_rule();
        SysUserContext.Moment moment = entity.get_moment();
        if (rule.equals(SysUserContext.Rule.MANAGER)) {
            insert(entity);
        }
        return true;
    }

    public SysTokenEntity login(SysUserEntity sysUserEntity) {
        String username = sysUserEntity.getUsername();
        String encodePassword = SecureUtil.md5(SecureUtil.md5(username) + sysUserEntity.getPassword());
        sysUserEntity.setPassword(encodePassword);
        sysUserEntity.set_condition(sysUserEntity.toCondition());
        sysUserEntity = findFirst(sysUserEntity);
        if (sysUserEntity == null) {
            SysTokenEntity token = sysTokenService.getLoginToken(sysUserEntity);
            return token;
        }
        return null;
    }
}
