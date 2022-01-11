package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseCondition;
import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseEntity;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.SpringUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserEntity extends SysUser implements BaseEntity<SysUser, SysUserEntity> {
    private static SysUserService sysUserService;
    private BaseCondition<?> _condition;

    public synchronized SysUserService getService() {
        if (sysUserService == null) {
            sysUserService = SpringUtil.getBean(SysUserService.class);
        }
        return sysUserService;
    }

    @Override
    public SysUser toPo() {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(this, sysUser, false);
        return sysUser;
    }

    @Override
    public SysUserEntity fromPo(SysUser po) {
        BeanUtil.copyProperties(po, this, false);
        return this;
    }
}
