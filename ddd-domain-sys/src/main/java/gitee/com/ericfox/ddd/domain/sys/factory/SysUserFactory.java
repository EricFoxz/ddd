package gitee.com.ericfox.ddd.domain.sys.factory;

import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserAgg;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserVo;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;

import java.util.List;

public class SysUserFactory {
    public static SysUserAgg createAgg(SysUser sysUser) {
        SysUserAgg sysUserAgg = new SysUserAgg();
        sysUserAgg.setSysUserVo(new SysUserVo());
        BeanUtil.copyProperties(sysUser, sysUserAgg.getSysUserVo(), false);
        return sysUserAgg;
    }

    public static List<SysUserAgg> createListAgg(List<SysUser> list) {
        List<SysUserAgg> result = CollUtil.newArrayList();
        for (SysUser sysUser : list) {
            result.add(createAgg(sysUser));
        }
        return result;
    }
}
