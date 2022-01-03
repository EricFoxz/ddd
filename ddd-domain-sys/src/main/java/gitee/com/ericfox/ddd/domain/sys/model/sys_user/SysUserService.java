package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import com.github.pagehelper.PageInfo;
import gitee.com.ericfox.ddd.domain.sys.factory.SysUserFactory;
import gitee.com.ericfox.ddd.domain.sys.model.BaseService;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.cache.CacheService;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.RepoService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("sysUserService")
@CacheConfig(cacheNames = "SysUserService")
public class SysUserService implements BaseService<SysUser> {
    @Resource
    RepoService repoService;

    // private final boolean useCache = SysUser.class.isAnnotationPresent(CacheEnabledAnnotation.class);

    @Resource
    private CacheService cacheService;

    @Transactional(readOnly = true)
    @Cacheable
    public SysUserAgg findById(Long id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser = repoService.findById(sysUser);
        if (sysUser != null) {
            return SysUserFactory.createAgg(sysUser);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public PageInfo<SysUserAgg> queryPage(SysUser sysUser, int pageNum, int pageSize) {
        PageInfo sysUserPageInfo = repoService.queryPage(sysUser, pageNum, pageSize);
        List<SysUserAgg> list = SysUserFactory.createListAgg(sysUserPageInfo.getList());
        sysUserPageInfo.setList(list);
        return (PageInfo<SysUserAgg>) sysUserPageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public void update() {

    }

    @CacheEvict(allEntries = true, beforeInvocation = false)
    public void cacheEvict() {
    }
}
