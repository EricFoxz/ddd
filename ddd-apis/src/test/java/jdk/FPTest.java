package jdk;

import gitee.com.ericfox.ddd.infrastructure.general.common.interfaces.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.coding.ClassUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;
import gitee.com.ericfox.ddd.infrastructure.persistent.service.repo.impl.MySqlRepoStrategy;

public class FPTest {
    public static void main(String[] args) {
        Class<BaseDao<SysUser>> daoClassByPo = ClassUtil.getDaoClassByPo(new SysUser(), new MySqlRepoStrategy());
        System.out.println(daoClassByPo);
    }
}
