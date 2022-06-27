package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.common.exceptions.ProjectFrameworkException;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.Constants;
import gitee.com.ericfox.ddd.common.toolkit.coding.SecureUtil;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_token.SysTokenService;
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
    @CacheEvict(allEntries = true, beforeInvocation = false, condition = "${entity.id > 0}")
    public SysUserEntity register(SysUserEntity entity) {
        // 判断用户名是否存在
        SysUserContext.Rule rule = entity.get_rule();
        SysUserContext.Moment moment = entity.get_moment();

        SysUserEntity query = new SysUserEntity();
        query.setUsername(entity.getUsername());
        query = findFirst(query);
        if (query != null) {
            throw new ProjectFrameworkException("用户名已存在", UNAUTHORIZED_401);
        }
        if (rule.equals(SysUserContext.Rule.WEB_USER)) {
            insert(entity);
        }
        return null;
    }

    public SysTokenEntity login(SysUserEntity sysUserEntity) {
        String username = sysUserEntity.getUsername();
        String encodePassword = SecureUtil.md5(SecureUtil.md5(username) + sysUserEntity.getPassword());
        sysUserEntity.setPassword(encodePassword);
        sysUserEntity.set_condition(sysUserEntity.toCondition());
        sysUserEntity = findFirst(sysUserEntity);
        if (sysUserEntity != null) {
            return sysTokenService.getLoginToken(sysUserEntity);
        }
        return null;
    }
}
