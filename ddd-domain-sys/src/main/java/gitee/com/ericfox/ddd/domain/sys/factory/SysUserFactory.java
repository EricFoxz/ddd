package gitee.com.ericfox.ddd.domain.sys.factory;

import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserAgg;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserEntity;
import gitee.com.ericfox.ddd.domain.sys.model.sys_user.SysUserVo;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.BeanUtil;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.CollUtil;

import java.util.List;

public class SysUserFactory {
    public static SysUserAgg createAgg(SysUserEntity sysUser) {
        SysUserAgg sysUserAgg = new SysUserAgg();
        sysUserAgg.setSysUserVo(new SysUserVo());
        BeanUtil.copyProperties(sysUser, sysUserAgg.getSysUserVo(), false);
        return sysUserAgg;
    }

    public static List<SysUserAgg> createListAgg(List<SysUserEntity> list) {
        List<SysUserAgg> result = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(list)) {
            for (SysUserEntity sysUser : list) {
                result.add(createAgg(sysUser));
            }
        }
        return result;
    }
}
