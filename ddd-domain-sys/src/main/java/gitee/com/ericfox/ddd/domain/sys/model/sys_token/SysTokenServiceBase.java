package gitee.com.ericfox.ddd.domain.sys.model.sys_token;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseContext;
import gitee.com.ericfox.ddd.common.interfaces.domain.BaseService;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.Constants;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysToken;
import gitee.com.ericfox.ddd.infrastructure.service.repo.RepoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@CacheConfig(cacheNames = "ServiceCache:SysTokenService", keyGenerator = Constants.SERVICE_CACHE_KEY_GENERATOR)
public abstract class SysTokenServiceBase implements BaseService<SysToken, SysTokenEntity>, BaseContext.BaseInteraction {
    @Resource
    RepoService repoService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.SERVICE_FUNCTION_CACHE_KEY_GENERATOR)
    public SysTokenEntity findById(Long id) {
        SysTokenEntity entity = new SysTokenEntity();
        entity.setId(id);
        return repoService.findById(entity);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.SERVICE_FUNCTION_CACHE_KEY_GENERATOR)
    public SysTokenEntity findFirst(SysTokenEntity entity) {
        return repoService.findFirst(entity);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.SERVICE_FUNCTION_CACHE_KEY_GENERATOR)
    public PageInfo<SysTokenEntity> queryPage(SysTokenEntity entity, int pageNum, int pageSize) {
        PageInfo<SysTokenEntity> pageInfo = repoService.queryPage(entity, pageNum, pageSize);
        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = Constants.SERVICE_FUNCTION_CACHE_KEY_GENERATOR)
    public List<SysTokenEntity> queryList(SysTokenEntity entity, int pageSize) {
        return repoService.queryList(entity, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public SysTokenEntity insert(SysTokenEntity entity) {
        return repoService.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean update(SysTokenEntity entity) {
        return repoService.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean deleteById(SysTokenEntity entity) {
        return repoService.deleteById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public boolean multiDeleteById(List<SysTokenEntity> entityList) {
        return repoService.multiDeleteById(entityList);
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public void cacheEvict() {
    }
}
