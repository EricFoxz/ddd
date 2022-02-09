package gitee.com.ericfox.ddd.apis.model.param.sys.sys_user;

import gitee.com.ericfox.ddd.apis.model.param.BaseDetailParam;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;

public class SysUserDetailParam implements BaseDetailParam<SysUser, SysUserEntity> {

    @Override
    public SysUserEntity toEntity() {
        SysUserEntity entity = new SysUserEntity();
        BeanUtil.copyProperties(this, entity, false);
        return entity;
    }
}
