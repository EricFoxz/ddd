package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseService;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@CacheConfig(cacheNames = "SysUserService", keyGenerator = Constants.KEY_GENERATOR)
public abstract class SysUserServiceBase implements BaseService<SysUserEntity> {
    @Resource
    RepoService repoService;

    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.KEY_GENERATOR_TO_SERVICE_PARAM)
    public SysUserEntity findById(Long id) {
        SysUserEntity sysUser = new SysUserEntity();
        sysUser.setId(id);
        return repoService.findById(sysUser);
    }

    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.KEY_GENERATOR_TO_SERVICE_PARAM)
    public PageInfo<SysUserEntity> queryPage(SysUserEntity sysUser, int pageNum, int pageSize) {
        PageInfo<SysUserEntity> sysUserPageInfo = repoService.queryPage(sysUser, pageNum, pageSize);
        return sysUserPageInfo;
    }

    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.KEY_GENERATOR_TO_SERVICE_PARAM)
    public List<SysUserEntity> queryList(SysUserEntity sysUser, int pageSize) {
        return repoService.queryList(sysUser, pageSize);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public SysUserEntity insert(SysUserEntity sysUser) {
        return repoService.insert(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean update(SysUserEntity sysUser) {
        return repoService.updateById(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean deleteById(SysUserEntity sysUser) {
        return repoService.deleteById(sysUser);
    }

    @CacheEvict(allEntries = true, beforeInvocation = false)
    public void cacheEvict() {
    }
}
