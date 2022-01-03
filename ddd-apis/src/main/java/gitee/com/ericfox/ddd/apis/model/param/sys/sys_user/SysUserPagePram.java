package gitee.com.ericfox.ddd.apis.model.param.sys.sys_user;

import gitee.com.ericfox.ddd.apis.model.param.BasePageParam;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserPagePram extends SysUser implements BasePageParam<SysUser> {
    private Integer pageNum;
    private Integer pageSize;

    @Override
    public SysUser toParent() {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(this, sysUser, false);
        return sysUser;
    }
}
