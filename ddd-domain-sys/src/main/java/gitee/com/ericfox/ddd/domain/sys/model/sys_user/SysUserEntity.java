package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;

public class SysUserEntity extends SysUserEntityBase {
    @Override
    public SysUser toPo() {
        return super.toPo();
    }

    @Override
    public SysUserEntity fromPo(SysUser po) {
        return super.fromPo(po);
    }
}
