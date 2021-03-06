package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseService;
import gitee.com.ericfox.ddd.infrastructure.general.common.Constants;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.infrastructure.service.repo.RepoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@CacheConfig(cacheNames = "ServiceCache:SysUserService", keyGenerator = Constants.SERVICE_CACHE_KEY_GENERATOR)
public abstract class SysUserServiceBase implements BaseService<SysUser, SysUserEntity>, BaseContext.Interaction {
    @Resource
    RepoService repoService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.SERVICE_FUNCTION_CACHE_KEY_GENERATOR)
    public SysUserEntity findById(Long id) {
        SysUserEntity entity = new SysUserEntity();
        entity.setId(id);
        return repoService.findById(entity);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.SERVICE_FUNCTION_CACHE_KEY_GENERATOR)
    public SysUserEntity findFirst(SysUserEntity entity) {
        return repoService.findFirst(entity);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.SERVICE_FUNCTION_CACHE_KEY_GENERATOR)
    public PageInfo<SysUserEntity> queryPage(SysUserEntity entity, int pageNum, int pageSize) {
        PageInfo<SysUserEntity> pageInfo = repoService.queryPage(entity, pageNum, pageSize);
        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.SERVICE_FUNCTION_CACHE_KEY_GENERATOR)
    public List<SysUserEntity> queryList(SysUserEntity entity, int pageSize) {
        return repoService.queryList(entity, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public SysUserEntity insert(SysUserEntity entity) {
        return repoService.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean update(SysUserEntity entity) {
        return repoService.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean deleteById(SysUserEntity entity) {
        return repoService.deleteById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean multiDeleteById(List<SysUserEntity> entityList) {
        return repoService.multiDeleteById(entityList);
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public void cacheEvict() {
    }
}
