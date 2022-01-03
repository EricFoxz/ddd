package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.domain.sys.factory.SysUserFactory;
import gitee.com.ericfox.ddd.domain.sys.model.BaseService;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.CacheEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.general.common.annos.OrmMysqlEnabledAnnotation;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.CacheService;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class SysUserService implements BaseService<SysUser> {
    @Resource
    RepoService repoService;

    private final boolean useCache = SysUser.class.isAnnotationPresent(CacheEnabledAnnotation.class);
    private final boolean useMysql = SysUser.class.isAnnotationPresent(OrmMysqlEnabledAnnotation.class);

    @Resource
    private CacheService cacheService;

    @Transactional
    public SysUserAgg findById(Long id) {
        SysUser sysUser = null;
        if (useCache) {
            sysUser = (SysUser) cacheService.get(id.toString());
        }
        if (sysUser == null && useMysql) {
            sysUser = new SysUser();
            sysUser.setId(id);
            sysUser = repoService.findById(sysUser);
            if (sysUser != null && useCache) {
                cacheService.set(id.toString(), sysUser);
            }
        }
        if (sysUser != null) {
            return SysUserFactory.createAgg(sysUser);
        }
        return null;
    }
}
