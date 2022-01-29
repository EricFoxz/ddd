package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseAgg;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserAgg extends SysUserEntity implements BaseAgg<SysUserEntity, SysUserAgg> {
    @Override
    public SysUserAgg fromPo(SysUser po) {
        return (SysUserAgg) super.fromPo(po);
    }

    @Override
    public SysUserAgg fromEntity(SysUserEntity entity) {
        return (SysUserAgg) super.fromPo(entity.getPo());
    }


}