package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.domain.sys.factory.SysUserFactory;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseService;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("sysUserService")
@CacheConfig(cacheNames = "SysUserService", keyGenerator = "keyGenerator")
public class SysUserService implements BaseService<SysUser, SysUserEntity> {
    @Resource
    RepoService repoService;

    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = "keyGeneratorToServiceParam")
    public SysUserAgg findById(Long id) {
        SysUserEntity sysUser = new SysUserEntity();
        sysUser.setId(id);
        sysUser = repoService.findById(sysUser);
        if (sysUser != null) {
            return SysUserFactory.createAgg(sysUser);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = "keyGeneratorToServiceParam")
    public PageInfo<SysUserAgg> queryPage(SysUserEntity sysUser, int pageNum, int pageSize) {
        PageInfo sysUserPageInfo = repoService.queryPage(sysUser, pageNum, pageSize);
        List<SysUserAgg> list = SysUserFactory.createListAgg(sysUserPageInfo.getList());
        sysUserPageInfo.setList(list);
        return (PageInfo<SysUserAgg>) sysUserPageInfo;
    }

    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = "keyGeneratorToServiceParam")
    public List<SysUserAgg> queryList(SysUserEntity sysUser, int pageSize) {
        List<SysUserEntity> sysUsers = repoService.queryList(sysUser, pageSize);
        return SysUserFactory.createListAgg(sysUsers);
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
