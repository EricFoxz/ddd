package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserEntity extends SysUserEntityBase {
    private SysUser po;

    @Override
    public SysUser toPo() {
        return super.toPo();
    }

    @Override
    public SysUserEntity fromPo(SysUser po) {
        return super.fromPo(po);
    }
}
