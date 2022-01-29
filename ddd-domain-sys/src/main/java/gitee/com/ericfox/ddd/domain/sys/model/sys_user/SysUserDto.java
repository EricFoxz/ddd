package gitee.com.ericfox.ddd.domain.sys.model.sys_user;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDto;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;

public class SysUserDto extends SysUser implements BaseDto<SysUser, SysUserEntity, SysUserDto> {
    public SysUserDto() {
    }

    public SysUserDto(SysUser sysUser) {
        SysUserEntity sysUserEntity = new SysUserEntity().fromPo(sysUser);
    }

    public SysUserDto(SysUserEntity sysUserEntity) {

    }
}