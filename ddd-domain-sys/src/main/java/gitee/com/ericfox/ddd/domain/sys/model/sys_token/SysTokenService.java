package gitee.com.ericfox.ddd.domain.sys.model.sys_token;

import gitee.com.ericfox.ddd.common.toolkit.coding.IdUtil;
import gitee.com.ericfox.ddd.common.toolkit.coding.StrUtil;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "ServiceCache:SysTokenService", keyGenerator = Constants.SERVICE_CACHE_KEY_GENERATOR)
public class SysTokenService extends SysTokenServiceBase {
    /**
     * 获取指定用户的登录令牌
     */
    public SysTokenEntity getLoginToken(SysUserEntity sysUserEntity) {
        SysTokenEntity condition = new SysTokenEntity();
        condition.setPlatform(sysUserEntity.getPlatform());
        condition.setUsername(sysUserEntity.getUsername());
        condition.set_condition(condition.toCondition());
        SysTokenEntity sysToken = findFirst(condition);
        long currentTimeMillis = System.currentTimeMillis();
        Long expireTime = currentTimeMillis + Constants.TOKEN_EXPIRE_MILLIS_TIME;
        if (sysToken != null) { //已有令牌，刷新时间
            if (sysToken.getExpireDate() != null && sysToken.getExpireDate() <= currentTimeMillis) { //令牌已经超时，需要更换
                return null;
            }
            sysToken.setExpireDate(expireTime);
            update(sysToken);
            return sysToken;
        } else { //没有令牌，创建一个
            condition.setToken(generateRandomToken(sysUserEntity));
            condition.setRefreshToken(generateRandomToken(sysUserEntity));
            condition.setCreateDate(currentTimeMillis);
            condition.setExpireDate(expireTime);
            insert(condition);
            return condition;
        }
    }

    /**
     * 随机生成令牌
     */
    public String generateRandomToken() {
        return generateRandomToken(null);
    }

    public String generateRandomToken(SysUserEntity sysUserEntity) {
        return IdUtil.randomUUID();
    }
}
