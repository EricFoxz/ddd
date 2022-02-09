package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseService;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@CacheConfig(cacheNames = "SysUserService", keyGenerator = Constants.KEY_GENERATOR)
public abstract class SysUserServiceBase implements BaseService<SysUser, SysUserEntity> {
    @Resource
    RepoService repoService;

    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.KEY_GENERATOR_TO_SERVICE_PARAM)
    public SysUserEntity findById(Long id) {
        SysUserEntity entity = new SysUserEntity();
        entity.setId(id);
        return repoService.findById(entity);
    }

    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.KEY_GENERATOR_TO_SERVICE_PARAM)
    public PageInfo<SysUserEntity> queryPage(SysUserEntity entity, int pageNum, int pageSize) {
        PageInfo<SysUserEntity> pageInfo = repoService.queryPage(entity, pageNum, pageSize);
        return pageInfo;
    }

    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.KEY_GENERATOR_TO_SERVICE_PARAM)
    public List<SysUserEntity> queryList(SysUserEntity entity, int pageSize) {
        return repoService.queryList(entity, pageSize);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public SysUserEntity insert(SysUserEntity entity) {
        return repoService.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean update(SysUserEntity entity) {
        return repoService.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean deleteById(SysUserEntity entity) {
        return repoService.deleteById(entity);
    }

    @CacheEvict(allEntries = true, beforeInvocation = false)
    public void cacheEvict() {
    }
}
