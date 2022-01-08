package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;

@Setter
@Getter
public class SysUserEntity extends SysUser implements BaseEntity<SysUser> {
    @Resource
    SysUserService sysUserService;

    @Override
    public synchronized SysUserService getDao() {
        if (sysUserService == null) {
            this.sysUserService = SpringUtil.getBean(SysUserService.class);
        }
        return sysUserService;
    }

    @Override
    public boolean create() {
        return sysUserService.insert(this) != null;
    }

    @Override
    public boolean delete() {
        return sysUserService.deleteById(this);
    }

    @Override
    public boolean edit() {
        return sysUserService.update(this);
    }
}
