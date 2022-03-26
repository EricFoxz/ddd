package jdk;

import gitee.com.ericfox.ddd.common.enums.strategy.RepoTypeStrategyEnum;
import gitee.com.ericfox.ddd.common.interfaces.infrastructure.BaseDao;
import gitee.com.ericfox.ddd.infrastructure.general.toolkit.trans.ClassTransUtil;
import gitee.com.ericfox.ddd.infrastructure.persistent.po.sys.SysUser;

public class FPTest {
    public static void main(String[] args) {
        Class<BaseDao<SysUser>> daoClassByPo = ClassTransUtil.getDaoClassByPo(new SysUser(), RepoTypeStrategyEnum.MY_SQL_REPO_STRATEGY);
        System.out.println(daoClassByPo);
    }
}
